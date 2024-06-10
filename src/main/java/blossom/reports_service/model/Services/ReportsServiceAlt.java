// package blossom.reports_service.model.Services;

// import java.util.Date;
// import java.util.Optional;

// import org.hibernate.StaleStateException;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.retry.annotation.Backoff;
// import org.springframework.retry.annotation.Retryable;
// import org.springframework.stereotype.*;
// import org.springframework.transaction.annotation.Transactional;

// import blossom.reports_service.inbound.ReportDTO;
// import blossom.reports_service.model.Entities.Challenge;
// import blossom.reports_service.model.Entities.ChallengeProgress;
// import blossom.reports_service.model.Entities.ChallengeReport;
// import blossom.reports_service.model.Entities.ChallengeSummary;
// import blossom.reports_service.model.Entities.User;
// import blossom.reports_service.model.Enums.ChallengeStatus;
// import blossom.reports_service.model.Enums.Visibility;
// import blossom.reports_service.model.Exceptions.AlreadyExistsException;
// import blossom.reports_service.model.Exceptions.NotFoundException;
// import
// blossom.reports_service.model.Repositories.ChallengeProgressRepository;
// import blossom.reports_service.model.Repositories.ChallengeReportRepository;
// import blossom.reports_service.model.Repositories.ChallengeRepository;
// import blossom.reports_service.model.Repositories.ChallengeSummaryRepository;
// import blossom.reports_service.model.Repositories.UserRepository;
// import jakarta.annotation.PostConstruct;
// import jakarta.annotation.PreDestroy;
// import jakarta.persistence.EntityNotFoundException;
// import jakarta.persistence.OptimisticLockException;

// @Service
// @Retryable(include = { OptimisticLockException.class,
// StaleStateException.class },
// // StaleStateException sometimes occurs when OptimisticLockException is
// expected
// // -> a bug!?
// maxAttempts = 3, // first attempt and 2 retries
// backoff = @Backoff(delay = 100, maxDelay = 500))
// public class ReportsService {
// private static final Logger LOGGER =
// LoggerFactory.getLogger(ReportsService.class);

// private final ChallengeProgressRepository challengeProgressRepository;
// private final ChallengeReportRepository challengeReportRepository;
// private final ChallengeSummaryRepository challengeSummaryRepository;
// private final UserRepository userRepository;
// private final ChallengeRepository challengeRepository;

// @Autowired
// public ReportsService(ChallengeProgressRepository
// challengeProgressRepository,
// ChallengeReportRepository challengeReportRepository,
// ChallengeSummaryRepository challengeSummaryRepository,
// UserRepository userRepository,
// ChallengeRepository challengeRepository) {

// this.challengeProgressRepository = challengeProgressRepository;
// this.challengeReportRepository = challengeReportRepository;
// this.challengeSummaryRepository = challengeSummaryRepository;
// this.userRepository = userRepository;
// this.challengeRepository = challengeRepository;
// }

// @PostConstruct
// public void init() {
// LOGGER.info("ReportsService initialized");
// }

// @PreDestroy
// public void destroy() {
// LOGGER.info("ReportsService destroyed");
// }

// // create challengeSummary for a new user
// @Transactional
// public ChallengeSummary createChallengeSummary(Long userId) {
// LOGGER.info("Creating ChallengeSummary for user with id: {}", userId);

// var userOptional = userRepository.findById(userId);

// if (userOptional.isEmpty()) {
// throw new NotFoundException("User not found");
// }
// var user = userOptional.get();

// if (challengeSummaryRepository.findByUserId(userId).isPresent()) {
// throw new AlreadyExistsException("ChallengeSummary already exists");
// }

// var challengeSummary = new ChallengeSummary(user);

// return challengeSummary;
// }

// // get the challengeSummary for a User
// @Transactional(readOnly = true)
// public ChallengeSummary getChallengeSummary(Long userId) {
// LOGGER.info("Getting ChallengeSummary for user with id: {}", userId);

// var userOptional = userRepository.findById(userId);
// var user = userOptional.orElseThrow(() -> new NotFoundException("User not
// found"));

// var challengeSummaryOptional = challengeSummaryRepository.findByUser(user);
// var challengeSummary = challengeSummaryOptional
// .orElseThrow(() -> new NotFoundException("ChallengeSummary not found"));

// return challengeSummary;
// }

// // create challengeReport for a new challenge
// @Transactional
// public ChallengeReport createChallengeReport(ReportDTO dto) {
// LOGGER.info("Creating ChallengeReport for the challenge with id {} for the
// user with id: {}. ",
// dto.getChallengeId(), dto.getUserId());

// // Check if user exists
// Optional<User> optionalUser = userRepository.findById(dto.getUserId());
// var user = optionalUser.orElseThrow(() -> new NotFoundException("User not
// found"));

// // Optional<Challenge> optionalChallenge =
// // challengeRepository.findById(dto.getChallengeId());
// // var challenge = optionalChallenge
// // .orElseThrow(() -> new NotFoundException("Challenge not found"));

// Optional<ChallengeProgress> optionalChallengeProgress =
// challengeProgressRepository.findById(dto.getChallengeId());
// var challengeProgress = optionalChallengeProgress
// .orElseThrow(() -> new NotFoundException("ChallengeProgress not found"));

// ChallengeReport challengeReport = new ChallengeReport(challengeProgress);

// // Check if ChallengeReport already exists for the given challengeId and
// userId
// boolean reportExists =
// challengeReportRepository.existsByChallengeIdAndUserId(
// dto.getChallengeId(), dto.getUserId());
// if (reportExists) {
// throw new AlreadyExistsException("ChallengeReport already exists");
// }

// var challengeSummary = challengeSummaryRepository.findByUser(user).get();
// challengeSummary.setChallengeCount(challengeSummary.getChallengeCount() + 1);
// challengeSummary.setPendingCount(challengeSummary.getPendingCount() + 1);

// challengeSummaryRepository.save(challengeSummary);

// // Save the ChallengeReport
// challengeReportRepository.save(challengeReport);

// LOGGER.info("ChallengeReport created successfully!");

// return challengeReport;
// }

// // get all challengeReports for a user
// @Transactional
// public Iterable<ChallengeReport> getChallengeReports(Long userId) {
// LOGGER.info("Getting all ChallengeReports for user with id: {}", userId);

// var userOptional = userRepository.findById(userId);
// // Check if User exists with orElseThrow
// var user = userOptional.orElseThrow(() -> new NotFoundException("User not
// found"));

// return challengeReportRepository.findAllByUser(user);
// }

// // update an challengeReport
// @Transactional
// public ChallengeReport updateChallengeReport(Long challengeId, Long
// challengeProgressId, String userEmail,
// double currentProgress, long timestamp) {

// LOGGER.info("Updating ChallengeReport with id: {}", challengeId);

// // Check if ChallengeReport exists
// Optional<ChallengeReport> optionalChallengeReport =
// challengeReportRepository.findById(challengeId);
// var challengeReport = optionalChallengeReport
// .orElseThrow(() -> new NotFoundException("ChallengeReport not found"));

// // Check if Challenge exists
// Optional<Challenge> optionalChallenge =
// challengeRepository.findById(challengeId);
// var challenge = optionalChallenge.orElseThrow(() -> new
// NotFoundException("Challenge not found"));

// Optional<Challenge> challengeOpt = challengeRepository.findById(challengeId);
// Optional<User> userOpt = userRepository.findByEmail(userEmail);
// Optional<ChallengeReport> reportOpt =
// challengeReportRepository.findByChallenge(challengeOpt.get());

// Date date = new Date();

// if (reportOpt.isPresent()) {
// ChallengeReport report = reportOpt.get();

// // Schritt 2: Neues Progress-Objekt erstellen und füllen
// ChallengeProgress progress = new ChallengeProgress(userOpt.get(),
// challengeOpt.get(), currentProgress,
// Visibility.PUBLIC);

// // Optional: Setze andere Felder wie Benutzer, Timestamp, etc. falls benötigt
// // progress.setUserEmail(userEmail);
// // progress.setTimestamp(timestamp);

// // Schritt 3: Neues Progress-Objekt dem Report hinzufügen
// report.getProgresses().add(progress);

// // Schritt 4: Den Report mit dem neuen Progress speichern
// reportRepository.save(report);

// // Progress speichern, wenn es separat vom Cascade-Typ der Beziehung
// behandelt
// // werden muss
// progressRepository.save(progress);
// } else {
// throw new EntityNotFoundException("Report mit ID " + challengeId + " nicht
// gefunden");
// }
// var challengeSummary = challengeSummaryRepository.findByUser(user).get();

// // Handle challenge completion status
// // if status is already DONE, do nothing
// if (dto.getStatus().equals(ChallengeStatus.DONE)) {
// challengeReport.setEndDate(date);
// challengeSummary.setDoneCount(challengeSummary.getDoneCount() + 1);
// challengeSummary.setPendingCount(challengeSummary.getPendingCount() - 1);

// // Decrement overdue count if the challenge was overdue
// if (date.after(challenge.getDeadline())) {
// challengeSummary.setOverdueCount(Math.max(0,
// challengeSummary.getOverdueCount() - 1));
// }
// } else {
// // Handle overdue status if the challenge is not done and overdue
// if (date.after(challenge.getDeadline())) {
// challengeSummary.setOverdueCount(challengeSummary.getOverdueCount() + 1);
// challengeReport.setStatus(ChallengeStatus.OVERDUE);
// }
// }

// // Update lastActive and consecutive days
// long currentTime = date.getTime();
// long lastActiveTime = challengeSummary.getLastActive().getTime();

// if (currentTime - lastActiveTime > 24 * 60 * 60 * 1000) {
// challengeSummary.setConsecutiveDays(0);
// } else {
// challengeSummary.setConsecutiveDays(challengeSummary.getConsecutiveDays() +
// 1);
// if (challengeSummary.getConsecutiveDays() >
// challengeSummary.getLongestStreak()) {
// challengeSummary.setLongestStreak(challengeSummary.getConsecutiveDays());
// }
// }

// challengeSummary.setLastActive(date);

// // Save and return the updated ChallengeReport
// return challengeReportRepository.save(challengeReport);
// }

// // delete an challengeReport
// @Transactional
// public void deleteChallengeReport(Long challengeReportId) {
// LOGGER.info("Deleting ChallengeReport with id: {}", challengeReportId);

// // Check if ChallengeReport exists
// ChallengeReport challengeReport =
// challengeReportRepository.findById(challengeReportId)
// .orElseThrow(() -> new NotFoundException("ChallengeReport not found"));

// // Check if User exists
// User user = userRepository.findById(challengeReport.getUserId())
// .orElseThrow(() -> new NotFoundException("User not found"));

// // Update ChallengeSummary attributes
// ChallengeSummary challengeSummary =
// challengeSummaryRepository.findByUser(user)
// .orElseThrow(() -> new NotFoundException("ChallengeSummary not found"));

// updateChallengeSummaryOnDelete(challengeSummary, challengeReport);

// // Delete the ChallengeReport
// challengeReportRepository.deleteById(challengeReportId);
// }

// private void updateChallengeSummaryOnDelete(ChallengeSummary
// challengeSummary, ChallengeReport challengeReport) {
// challengeSummary.setPendingCount(challengeSummary.getPendingCount() - 1);
// challengeSummary.setTotalChallenges(challengeSummary.getTotalChallenges() -
// 1);

// if (challengeReport.getStatus() == ChallengeStatus.OVERDUE) {
// challengeSummary.setOverdueCount(challengeSummary.getOverdueCount() - 1);
// } else if (challengeReport.getStatus() == ChallengeStatus.DONE) {
// challengeSummary.setDoneCount(challengeSummary.getDoneCount() - 1);
// }

// // Save the updated ChallengeSummary
// challengeSummaryRepository.save(challengeSummary);
// }
// }

// // update challenge progress
// // @Transactional
// // public void updateChallengeProgress(Long challengeProgressId, String
// // userEmail, Double progress, Date timestamp) {
// // LOGGER.info("Updating ChallengeProgress for challengeProgress with id:
// // {}",
// // // challengeProgressId);

// // // // Check if ChallengeProgress exists
// // // Optional<ChallengeProgress> optionalChallengeProgress =
// // // challengeProgressRepository.findById(challengeProgressId);
// // // var challenge = optionalChallengeProgress.orElseThrow(() -> new
// // // NotFoundException("ChallengeProgress not found"));

// // // // Check if User exists
// // // Optional<User> optionalUser = userRepository.findByEmail(userEmail);
// // // if (optionalUser.isEmpty()) {
// // // throw new NotFoundException("User not found");
// // // }

// // // // Check if ChallengeReport exists
// // // Optional<ChallengeReport> optionalChallengeReport =
// // // challengeReportRepository.findByChallengeProgress(challenge);
// // // var challengeReport = optionalChallengeReport.orElseThrow(() -> new
// // // NotFoundException("ChallengeReport not found"));
// // // }
// // }
