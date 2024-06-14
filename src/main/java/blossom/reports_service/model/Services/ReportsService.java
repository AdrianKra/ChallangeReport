package blossom.reports_service.model.Services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.hibernate.StaleStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import blossom.reports_service.model.Entities.Challenge;
import blossom.reports_service.model.Entities.ChallengeProgress;
import blossom.reports_service.model.Entities.ChallengeReport;
import blossom.reports_service.model.Entities.ChallengeSummary;
import blossom.reports_service.model.Entities.User;
import blossom.reports_service.model.Enums.ChallengeStatus;
import blossom.reports_service.model.Enums.Visibility;
import blossom.reports_service.model.Exceptions.NotFoundException;
import blossom.reports_service.model.Repositories.ChallengeReportRepository;
import blossom.reports_service.model.Repositories.ChallengeSummaryRepository;
import jakarta.persistence.OptimisticLockException;
import org.springframework.transaction.annotation.Transactional;

@Service
@Retryable(include = { OptimisticLockException.class,
    StaleStateException.class }, maxAttempts = 3, // first attempt and 2 retries
    backoff = @Backoff(delay = 100, maxDelay = 500))
public class ReportsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ReportsService.class);

  private final ChallengeReportRepository challengeReportRepository;
  private final ChallengeSummaryRepository challengeSummaryRepository;
  private RestTemplate restTemplate;

  @Autowired
  public ReportsService(
      ChallengeReportRepository challengeReportRepository,
      ChallengeSummaryRepository challengeSummaryRepository,
      RestTemplate restTemplate) {

    this.challengeReportRepository = challengeReportRepository;
    this.challengeSummaryRepository = challengeSummaryRepository;
    this.restTemplate = restTemplate;
  }

  private static final String USER_SERVICE_URL = "http://localhost:8080/rest/users";
  private static final String CHALLENGE_SERVICE_URL = "http://localhost:8080/rest/challenge";

  @Transactional
  public void createChallengeSummary(String userEmail) {
    LOGGER.info("Creating ChallengeSummary for user with id: {}", userEmail);

    // Call the user service to register a new user
    String url = USER_SERVICE_URL + "/getUserByEmail/" + userEmail;
    User user = restTemplate.getForObject(url, User.class);

    // Create a new ChallengeSummary for the user
    ChallengeSummary summary = new ChallengeSummary(user);
    challengeSummaryRepository.save(summary);
  }

  @Transactional(readOnly = true)
  public ChallengeSummary getChallengeSummary(String userEmail) {
    LOGGER.info("Getting ChallengeSummary for user with id: {}", userEmail);

    // Fetch User from the user service
    String url = USER_SERVICE_URL + "/getUserByEmail/" + userEmail;
    User user = restTemplate.getForObject(url, User.class);

    var summaryOpt = challengeSummaryRepository.findByUser(user);
    var summary = summaryOpt
        .orElseThrow(() -> new NotFoundException("ChallengeSummary not found"));

    return summary;
  }

  @Transactional
  public void updateChallengeProgress(Long challengeId, Long challengeProgressId, String userEmail,
      Double currentProgress, Date timestamp) {
    LOGGER.info("Updating ChallengeProgress with id: {} for user with email: {}", challengeProgressId, userEmail);

    // Fetch User from the user service
    String url = USER_SERVICE_URL + "/getUserByEmail/" + userEmail;
    User user = restTemplate.getForObject(url, User.class);

    // Fetch Challenge from the challenge service
    url = CHALLENGE_SERVICE_URL + "/getChallengeById/" + challengeId;
    Challenge challenge = restTemplate.getForObject(url, Challenge.class);

    // Create new ChallengeProgress
    ChallengeProgress progress = new ChallengeProgress(user, challenge, currentProgress, Visibility.FRIENDS);

    Optional<ChallengeReport> reportOpt = challengeReportRepository.findByChallenge(challenge);
    var report = reportOpt.orElseGet(() -> createChallengeReport(challengeId, userEmail));

    // Add progress to the report
    report.addProgress(progress);
  }

  @Transactional
  public ChallengeReport createChallengeReport(Long challengeId, String userEmail) {
    LOGGER.info("Creating ChallengeReport for user with Email: {} and challenge with id: {}", userEmail, challengeId);

    // Fetch User from the user service
    String url = USER_SERVICE_URL + "/getUserByEmail/" + userEmail;
    User user = restTemplate.getForObject(url, User.class);

    // Fetch Challenge from the challenge service
    url = CHALLENGE_SERVICE_URL + "/getChallengeById/" + challengeId;
    Challenge challenge = restTemplate.getForObject(url, Challenge.class);

    // Create new ChallengeReport
    ChallengeReport report = new ChallengeReport(user, challenge);

    var summaryOpt = challengeSummaryRepository.findByUser(user);
    var summary = summaryOpt
        .orElseThrow(() -> new NotFoundException("ChallengeSummary not found"));

    summary.setChallengeCount(summary.getChallengeCount() + 1);
    summary.setPendingCount(summary.getPendingCount() + 1);

    challengeSummaryRepository.save(summary);

    challengeReportRepository.save(report);

    return report;
  }

  @Transactional(readOnly = true)
  public Iterable<ChallengeReport> getChallengeReports(String userEmail) {
    LOGGER.info("Getting all ChallengeReports for user with id: {}", userEmail);

    // Fetch User from the user service
    String url = USER_SERVICE_URL + "/getUserByEmail/" + userEmail;
    User user = restTemplate.getForObject(url, User.class);

    return challengeReportRepository.findAllByUser(user);
  }

  @SuppressWarnings("null")
  @Transactional
  public ChallengeReport updateReportStatus(Long challengeId, Long challengeProgressId, String userEmail,
      double currentProgress, long timestamp) {

    LOGGER.info("Updating ChallengeReport with id: {}", challengeId);

    // Check if ChallengeReport exists
    Optional<ChallengeReport> reportOpt = challengeReportRepository.findById(challengeId);
    ChallengeReport challengeReport = reportOpt.orElseThrow(() -> new NotFoundException("ChallengeReport not found"));

    // Fetch User from the user service
    String userUrl = USER_SERVICE_URL + "/getUserByEmail/" + userEmail;
    User user = restTemplate.getForObject(userUrl, User.class);

    // Fetch Challenge from the challenge service
    String challengeUrl = CHALLENGE_SERVICE_URL + "/" + challengeId;
    Challenge challenge = restTemplate.getForObject(challengeUrl, Challenge.class);

    // Update ChallengeSummary
    ChallengeSummary challengeSummary = challengeSummaryRepository.findByUser(user)
        .orElseThrow(() -> new NotFoundException("ChallengeSummary not found"));

    // Handle challenge completion status
    Date currentDate = new Date();
    if (challengeReport.getStatus() == ChallengeStatus.DONE) {
      challengeReport.setEndDate(currentDate);
      challengeSummary.incrementDoneCount();
      challengeSummary.decrementPendingCount();

      if (currentDate.after(challenge.getDeadline())) {
        challengeSummary.decrementOverdueCount();
      }
    } else if (currentDate.after(challenge.getDeadline())) {
      challengeSummary.incrementOverdueCount();
      challengeReport.setStatus(ChallengeStatus.OVERDUE);
    }

    // Update lastActive and consecutive days
    long currentTime = currentDate.getTime();
    long lastActiveTime = challengeSummary.getLastActive().getTime();
    if (currentTime - lastActiveTime > 24 * 60 * 60 * 1000) {
      challengeSummary.resetConsecutiveDays();
    } else {
      challengeSummary.incrementConsecutiveDays();
      if (challengeSummary.getConsecutiveDays() > challengeSummary.getLongestStreak()) {
        challengeSummary.updateLongestStreak();
      }
    }
    challengeSummary.updateLastActive(currentDate);

    // Save ChallengeReport and ChallengeSummary
    challengeSummaryRepository.save(challengeSummary);
    return challengeReportRepository.save(challengeReport);
  }

  @Transactional
  public void deleteChallengeReport(Long challengeReportId) {
    LOGGER.info("Deleting ChallengeReport with id: {}", challengeReportId);

    // Check if ChallengeReport exists
    ChallengeReport report = challengeReportRepository.findById(challengeReportId)
        .orElseThrow(() -> new NotFoundException("ChallengeReport not found"));

    // Update ChallengeSummary attributes
    ChallengeSummary summary = challengeSummaryRepository.findByUser(report.getUser())
        .orElseThrow(() -> new NotFoundException("ChallengeSummary not found"));

    summary.decrementPendingCount();
    summary.decrementChallengeCount();

    if (report.getStatus().equals(ChallengeStatus.OVERDUE)) {
      summary.decrementOverdueCount();
    } else if (report.getStatus().equals(ChallengeStatus.DONE)) {
      summary.decrementDoneCount();
    }

    // Delete the ChallengeReport
    challengeReportRepository.deleteById(challengeReportId);
  }
}
