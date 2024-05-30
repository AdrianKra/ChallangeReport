package blossom.reports_service.model;

import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import blossom.reports_service.inbound.ReportDTO;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;

@Service
public class ReportsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ReportsService.class);

  private final ChallengeReportRepository challengeReportRepository;
  private final ChallengeSummaryRepository challengeSummaryRepository;
  private final UserRepository userRepository;
  private final ChallengeRepository challengeRepository;

  @Autowired
  public ReportsService(ChallengeReportRepository challengeReportRepository,
      ChallengeSummaryRepository challengeSummaryRepository,
      UserRepository userRepository,
      ChallengeRepository challengeRepository) {

    this.challengeReportRepository = challengeReportRepository;
    this.challengeSummaryRepository = challengeSummaryRepository;
    this.userRepository = userRepository;
    this.challengeRepository = challengeRepository;
  }

  @PostConstruct
  public void init() {
    LOGGER.info("ReportsService initialized");
  }

  @PreDestroy
  public void destroy() {
    LOGGER.info("ReportsService destroyed");
  }

  // create challengeSummary for a new user
  @Transactional
  public ChallengeSummary createChallengeSummary(Long userId) {
    LOGGER.info("Creating ChallengeSummary for user with id: {}", userId);

    var userOptional = userRepository.findById(userId);

    if (userOptional.isEmpty()) {
      throw new NotFoundException("User not found");
    }
    var user = userOptional.get();

    if (challengeSummaryRepository.findByUserId(userId).isPresent()) {
      throw new AlreadyExistsException("ChallengeSummary already exists");
    }

    var challengeSummary = new ChallengeSummary(user);
    return challengeSummary;
  }

  // get the challengeSummary for a User
  @Transactional
  public ChallengeSummary getChallengeSummary(Long userId) {
    LOGGER.info("Getting ChallengeSummary for user with id: {}", userId);

    var userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) {
      throw new NotFoundException("User not found");
    }
    var user = userOptional.get();

    var challengeSummaryOptional = challengeSummaryRepository.findByUser(user);
    if (challengeSummaryOptional.isEmpty()) {
      throw new NotFoundException("ChallengeSummary not found");
    }
    var challengeSummary = challengeSummaryOptional.get();

    return challengeSummary;
  }

  // create challengeReport for a new challenge
  @Transactional
  public ChallengeReport createChallengeReport(ReportDTO dto) {
    LOGGER.info("Creating ChallengeReport for user with id: {}", dto.getUserId());

    // Convert DTO to ChallengeReport entity
    ModelMapper modelMapper = new ModelMapper();
    ChallengeReport challengeReport = modelMapper.map(dto, ChallengeReport.class);

    // Check if user exists
    Optional<User> optionalUser = userRepository.findById(challengeReport.getUser().getId());
    if (optionalUser.isEmpty()) {
      throw new NotFoundException("User not found");
    }
    var user = optionalUser.get();

    Optional<Challenge> optionalChallenge = challengeRepository.findById(challengeReport.getChallenge().getId());
    if (optionalChallenge.isEmpty()) {
      throw new NotFoundException("Challenge not found");
    }
    var challenge = optionalChallenge.get();

    // Check if ChallengeReport already exists for the given challengeId and userId
    boolean reportExists = challengeReportRepository.existsByChallengeIdAndUserId(
        challengeReport.getChallenge().getId(), dto.getUserId());
    if (reportExists) {
      throw new AlreadyExistsException("ChallengeReport already exists");
    }

    var challengeSummary = challengeSummaryRepository.findByUser(user).get();
    challengeSummary.setChallengeCount(challengeSummary.getChallengeCount() + 1);
    challengeSummary.setPendingCount(challengeSummary.getPendingCount() + 1);

    return challengeReport;
  }

  // get all challengeReports for a user
  @Transactional
  public Iterable<ChallengeReport> getChallengeReports(Long userId) {
    LOGGER.info("Getting all ChallengeReports for user with id: {}", userId);

    var userOptional = userRepository.findById(userId);
    // Check if User exists with orElseThrow
    var user = userOptional.orElseThrow(() -> new NotFoundException("User not found"));

    return challengeReportRepository.findAllByUser(user);
  }

  // update an challengeReport
  @Transactional
  public ChallengeReport updateChallengeReport(Long challengeReportId, ReportDTO dto) {
    LOGGER.info("Updating ChallengeReport with id: {}", challengeReportId);

    // Check if ChallengeReport exists
    Optional<ChallengeReport> optionalChallengeReport = challengeReportRepository.findById(challengeReportId);
    if (optionalChallengeReport.isEmpty()) {
      throw new NotFoundException("ChallengeReport not found");
    }

    // Check if Challenge exists
    Optional<Challenge> optionalChallenge = challengeRepository.findById(dto.getChallengeId());
    if (optionalChallenge.isEmpty()) {
      throw new NotFoundException("Challenge not found");
    }

    var userOptional = userRepository.findById(dto.getUserId());
    if (userOptional.isEmpty()) {
      throw new NotFoundException("User not found");
    }

    // Convert DTO to ChallengeReport entity
    ModelMapper modelMapper = new ModelMapper();
    ChallengeReport challengeReport = modelMapper.map(dto, ChallengeReport.class);

    // update challengeSummary attributes
    Date date = new Date();

    // done
    var challengeSummary = challengeSummaryRepository.findByUser(userOptional.get()).get();
    if (challengeReport.getStatus().equals(ChallengeStatus.DONE)) {
      challengeSummary.setDoneCount(challengeSummary.getDoneCount() + 1);
      challengeSummary.setPendingCount(challengeSummary.getPendingCount() - 1);
      // update OverdueCount if the challenge was overdue
      if (date.after(challengeReport.getEndDate())) {
        challengeSummary.setOverdueCount(challengeSummary.getOverdueCount() - 1);
      }
    }
    // overdue
    if (date.after(challengeReport.getEndDate())) {
      challengeSummary.setOverdueCount(challengeSummary.getOverdueCount() + 1);
      challengeReport.setStatus(ChallengeStatus.OVERDUE);
    }
    // update lastActive and streaks
    challengeSummary.setConsecutiveDays(challengeSummary.getConsecutiveDays() + 1);
    if (challengeSummary.getConsecutiveDays() > challengeSummary.getLongestStreak()) {
      challengeSummary.setLongestStreak(challengeSummary.getConsecutiveDays());
    }
    challengeSummary.setLastActive(date);

    // Save and return the updated ChallengeReport
    return challengeReportRepository.save(challengeReport);
  }

  // delete an challengeReport
  @Transactional
  public void deleteChallengeReport(Long challengeReportId) {
    LOGGER.info("Deleting ChallengeReport with id: {}", challengeReportId);

    // Check if ChallengeReport exists
    Optional<ChallengeReport> optionalChallengeReport = challengeReportRepository.findById(challengeReportId);
    if (optionalChallengeReport.isEmpty()) {
      throw new NotFoundException("ChallengeReport not found");
    }

    // Check if User exists
    Optional<User> optionalUser = userRepository.findById(optionalChallengeReport.get().getUser().getId());
    var user = optionalUser.orElseThrow(() -> new NotFoundException("User not found"));

    // update challengeSummary attributes
    var challengeSummary = challengeSummaryRepository.findByUser(user).get();
    // if (optionalChallengeReport.get().getStatus().equals(ChallengeStatus.DONE)) {
    // challengeSummary.setDoneCount(challengeSummary.getDoneCount() - 1);
    // } else {
    challengeSummary.setPendingCount(challengeSummary.getPendingCount() - 1);
    // }
    if (optionalChallengeReport.get().getStatus().equals(ChallengeStatus.OVERDUE)) {
      challengeSummary.setOverdueCount(challengeSummary.getOverdueCount() - 1);
    }
    challengeSummary.setChallengeCount(challengeSummary.getChallengeCount() - 1);

    // delete the ChallengeReport
    challengeReportRepository.deleteById(challengeReportId);
  }

  // update challenge progress
  @Transactional
  public void updateChallengeProgress(Long challengeId, String userEmail, Double progress, String timestamp) {
    LOGGER.info("Updating ChallengeProgress for challenge with id: {}", challengeId);

    // Check if Challenge exists
    Optional<Challenge> optionalChallenge = challengeRepository.findById(challengeId);
    var challenge = optionalChallenge.orElseThrow(() -> new NotFoundException("Challenge not found"));

    // Check if User exists
    Optional<User> optionalUser = userRepository.findByEmail(userEmail);
    if (optionalUser.isEmpty()) {
      throw new NotFoundException("User not found");
    }

    // Check if ChallengeReport exists
    Optional<ChallengeReport> optionalChallengeReport = challengeReportRepository.findByChallenge(challenge);
    var challengeReport = optionalChallengeReport.orElseThrow(() -> new NotFoundException("ChallengeReport not found"));

    // update progress and timestamp
    challengeReport.addProgress(progress);
    challengeReport.addTimestamp(timestamp);
  }

  // // sort challengeReports by startDate
  // public Iterable<ChallengeReport> sortChallengeReportsByStartDate(Long userId)
  // {
  // var userOptional = userRepository.findById(userId);
  // if (userOptional.isEmpty()) {
  // throw new NotFoundException("User not found");
  // }

  // Iterable<ChallengeReport> challengeReports =
  // challengeReportRepository.findAllByUser(userOptional.get());

  // List<ChallengeReport> challengeList = toList(challengeReports);
  // challengeList.sort(Comparator.comparing(ChallengeReport::getStartDate));

  // return challengeList;
  // }

  // // turn generic Iterable into List
  // public <T> List<T> toList(Iterable<T> iterable) {
  // List<T> list = new ArrayList<T>();
  // iterable.forEach(list::add);
  // return list;
  // }
}
