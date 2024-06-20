package blossom.reports_service.model.Services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.StaleStateException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import blossom.reports_service.inbound.DTOs.ChallengeDTO;
import blossom.reports_service.model.Entities.Challenge;
import blossom.reports_service.model.Entities.ChallengeProgress;
import blossom.reports_service.model.Entities.ChallengeReport;
import blossom.reports_service.model.Entities.ChallengeSummary;
import blossom.reports_service.model.Entities.User;
import blossom.reports_service.model.Enums.ChallengeStatus;
import blossom.reports_service.model.Enums.Visibility;
import blossom.reports_service.model.Exceptions.AuthenticationFailedException;
import blossom.reports_service.model.Exceptions.NotFoundException;
import blossom.reports_service.model.Exceptions.UnauthorizedException;
import blossom.reports_service.model.Repositories.ChallengeReportRepository;
import blossom.reports_service.model.Repositories.ChallengeSummaryRepository;
import jakarta.persistence.OptimisticLockException;

@Service
@Retryable(include = { OptimisticLockException.class,
    StaleStateException.class }, maxAttempts = 3, backoff = @Backoff(delay = 100, maxDelay = 500))
public class ReportsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ReportsService.class);

  private final ChallengeReportRepository challengeReportRepository;
  private final ChallengeSummaryRepository challengeSummaryRepository;
  private RestTemplate restTemplate;

  private RetryableFriendshipApiClient retryableFriendshipApiClient;

  @Autowired
  public ReportsService(
      ChallengeReportRepository challengeReportRepository,
      ChallengeSummaryRepository challengeSummaryRepository,
      RestTemplate restTemplate,
      RetryableFriendshipApiClient retryableFriendshipApiClient) {

    this.challengeReportRepository = challengeReportRepository;
    this.challengeSummaryRepository = challengeSummaryRepository;
    this.restTemplate = restTemplate;
    this.retryableFriendshipApiClient = retryableFriendshipApiClient;
  }

  private static final String USER_SERVICE_URL = "http://localhost:8080/rest/users";
  private static final String CHALLENGE_SERVICE_URL = "http://localhost:8081/rest/challenge";

  /**
   * Create a new ChallengeSummary for the user with the given email
   * 
   * @param userEmail
   */
  @Transactional
  public void createChallengeSummary(String userEmail) {
    LOGGER.info("Creating ChallengeSummary for user with id: {}", userEmail);

    // Call the user service to register a new user
    String url = USER_SERVICE_URL + "/getUserByEmail/" + userEmail;
    User user = restTemplate.getForObject(url, User.class);

    // Create a new ChallengeSummary for the user
    ChallengeSummary summary = new ChallengeSummary(user);
    challengeSummaryRepository.save(summary); // ???
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

  /**
   * Update the ChallengeProgress with the given id for the user with the given
   * email
   * 
   * @param challengeId
   * @param challengeProgressId
   * @param userEmail
   * @param currentProgress
   * @param timestamp
   */
  @Transactional
  public void updateChallengeProgress(Long challengeId, Long challengeProgressId, String userEmail,
      Double currentProgress, Date timestamp) {
    LOGGER.info("Updating ChallengeProgress with id: {} for user with email: {}", challengeProgressId, userEmail);

    // Fetch User from the user service
    String url = USER_SERVICE_URL + "/getUserByEmail/" + userEmail;
    User user = restTemplate.getForObject(url, User.class);

    // Fetch Challenge from the challenge service
    url = CHALLENGE_SERVICE_URL + "/getChallengeById/" + challengeId;
    ChallengeDTO challengeDTO = restTemplate.getForObject(url, ChallengeDTO.class);

    // map to Challenge
    ModelMapper modelMapper = new ModelMapper();
    Challenge challenge = modelMapper.map(challengeDTO, Challenge.class);

    // Create new ChallengeProgress
    ChallengeProgress progress = new ChallengeProgress(user, challenge, currentProgress, Visibility.FRIENDS);

    Optional<ChallengeReport> reportOpt = challengeReportRepository.findByChallenge(challenge);
    var report = reportOpt.orElseGet(() -> createChallengeReport(userEmail, challengeId));

    // Add progress to the report
    report.addProgress(timestamp, progress);
  }

  /**
   * Create a new ChallengeReport for the user with the given email and the
   * challenge with the given id
   * 
   * @param challengeId
   * @param userEmail
   * @return
   */
  @Transactional
  public ChallengeReport createChallengeReport(String userEmail, Long challengeId) {
    LOGGER.info("Creating ChallengeReport for user with Email: {} and challenge with id: {}", userEmail, challengeId);

    // Fetch User from the user service
    String url = USER_SERVICE_URL + "/getUserByEmail/" + userEmail;
    User user = restTemplate.getForObject(url, User.class);

    // Fetch Challenge from the challenge service
    url = CHALLENGE_SERVICE_URL + "/getChallengeById/" + challengeId;
    ChallengeDTO challengeDTO = restTemplate.getForObject(url, ChallengeDTO.class);

    // map to Challenge
    ModelMapper modelMapper = new ModelMapper();
    Challenge challenge = modelMapper.map(challengeDTO, Challenge.class);

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

  /**
   * Get all ChallengeReports for the user with the given email
   * 
   * @param userEmail
   * @return
   */
  @SuppressWarnings("null")
  @Transactional(readOnly = true)
  public Iterable<ChallengeReport> getChallengeReports(String userEmail) {
    LOGGER.info("Getting all ChallengeReports for user with id: {}", userEmail);

    // Get the authenticated user's email
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String authenticatedUserEmail = null;

    // Get the authenticated user's email from the authentication object
    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      authenticatedUserEmail = userDetails.getUsername();
    } else if (authentication != null && authentication.getPrincipal() instanceof String) {
      authenticatedUserEmail = (String) authentication.getPrincipal();
    }

    if (authenticatedUserEmail == null) {
      throw new AuthenticationFailedException("Unable to retrieve authenticated user's email");
    }

    // Check if the user is authorized (creator or friend)
    Boolean isFriend = restTemplate.getForObject(
        USER_SERVICE_URL + "/friendship/checkFriendship/" + authenticatedUserEmail + "/" + userEmail,
        Boolean.class);

    if (!isFriend) {
      throw new UnauthorizedException("User is not authorized to view challenge reports for this user");
    }

    // Fetch User from the user service
    String url = USER_SERVICE_URL + "/getUserByEmail/" + userEmail;
    User user = restTemplate.getForObject(url, User.class);

    return challengeReportRepository.findAllByUser(user);

  }

  /**
   * Update the ChallengeReport with the given id for the user with the given
   * email
   * 
   * @param challengeId
   * @param challengeProgressId
   * @param userEmail
   * @param currentProgress
   * @param timestamp
   * @return
   */
  @Transactional
  public ChallengeReport updateReportStatus(Long challengeId, Long challengeProgressId, String userEmail,
      Double currentProgress, Date timestamp) {

    LOGGER.info("Updating ChallengeReport with id: {}", challengeId);

    // Check if ChallengeReport exists
    Optional<ChallengeReport> reportOpt = challengeReportRepository.findById(challengeId);
    ChallengeReport challengeReport = reportOpt.orElseThrow(() -> new NotFoundException("ChallengeReport not found"));

    // Fetch User from the user service
    String userUrl = USER_SERVICE_URL + "/getUserByEmail/" + userEmail;
    User user = restTemplate.getForObject(userUrl, User.class);

    // Fetch Challenge from the challenge service
    String challengeUrl = CHALLENGE_SERVICE_URL + "/" + challengeId;
    ChallengeDTO challengeDTO = restTemplate.getForObject(challengeUrl, ChallengeDTO.class);

    // map to Challenge
    ModelMapper modelMapper = new ModelMapper();
    Challenge challenge = modelMapper.map(challengeDTO, Challenge.class);

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

  /**
   * Delete the ChallengeReport with the given id
   * 
   * @param challengeReportId
   */
  @Transactional
  public void deleteChallengeReport(String userEmail, Long challengeReportId) {
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

    challengeSummaryRepository.save(summary);

    // Delete the ChallengeReport
    challengeReportRepository.deleteById(challengeReportId);

  }

  /**
   * Get the total progress of the ChallengeReport
   * 
   * @param userEmail
   * @param challengeReport
   * @return
   */
  @Transactional(readOnly = true)
  public double getTotalProgress(String userEmail, Long challengeReportId) {
    LOGGER.info("Getting total progress for ChallengeReport with id: {}", challengeReportId);

    // Fetch ChallengeReport from the database
    ChallengeReport challengeReport = challengeReportRepository.findById(challengeReportId)
        .orElseThrow(() -> new NotFoundException("ChallengeReport not found"));

    // Fetch User from the user service
    String url = USER_SERVICE_URL + "/getUserByEmail/" + userEmail;
    User user = restTemplate.getForObject(url, User.class);

    // Check if the user is allowed to read the content
    if (!canRead(user, challengeReport.getUser(), challengeReport.getChallenge().getChallengeVisibility())) {
      throw new UnauthorizedException("User is not allowed to read the content");
    }

    return challengeReport.getProgressList().values().stream().mapToDouble(progress -> progress.getCurrentProgress())
        .sum();
  }

  /**
   * Get the average daily progress of the ChallengeReport
   * 
   * @param userEmail
   * @param challengeReport
   * @return
   */
  @Transactional(readOnly = true)
  public double getAverageDailyProgress(String userEmail, Long challengeId) {
    LOGGER.info("Getting average daily progress for ChallengeReport with id: {}", challengeId);

    // Fetch Challenge from the challenge service
    String url = CHALLENGE_SERVICE_URL + "/getChallengeById/" + challengeId;
    ChallengeDTO challengeDTO = restTemplate.getForObject(url, ChallengeDTO.class);

    // map to Challenge
    ModelMapper modelMapper = new ModelMapper();
    Challenge challenge = modelMapper.map(challengeDTO, Challenge.class);

    User user = restTemplate.getForObject(USER_SERVICE_URL + "/getUserByEmail/" + userEmail, User.class);

    ChallengeReport challengeReport = challengeReportRepository.findByChallenge(challenge)
        .orElseThrow(() -> new NotFoundException("ChallengeReport not found"));

    // get challengeSummary for user
    ChallengeSummary challengeSummary = challengeSummaryRepository.findByUser(user)
        .orElseThrow(() -> new NotFoundException("ChallengeSummary not found"));

    long activeDays = challengeSummary.getConsecutiveDays();
    return activeDays == 0 ? 0 : getTotalProgress(userEmail, challengeReport.getId()) / activeDays;
  }

  /***
   * Helper method to check if the user is allowed to read the content
   * 
   * @param currentUser   The user who wants to read the content
   * @param contentAuthor The author of the content
   * @param visibility    The visibility of the content
   * @return true if the user is allowed to read the content
   * @return false if the user is not allowed to read the content
   */
  private boolean canRead(User currentUser, User contentAuthor, Visibility visibility) {
    if (visibility == Visibility.PUBLIC) {
      LOGGER.info(
          "User with email " + currentUser.getEmail() + " is allowed to read content, because the content is public.");
      return true;
    }
    if (visibility == Visibility.PRIVATE) {
      if (currentUser.getEmail().equals(contentAuthor.getEmail())) {
        LOGGER.info("User with email " + currentUser.getEmail()
            + " is allowed to read content, because the content is private and the user is the author.");
        return true;
      } else {
        LOGGER.error("User with email " + currentUser.getEmail()
            + " is not allowed to read content, because the content is private and the user is not the author.");
        return false;
      }
    }
    if (visibility == Visibility.FRIENDS) {
      if (currentUser.getEmail().equals(contentAuthor.getEmail())) {
        LOGGER.info("User with email " + currentUser.getEmail()
            + " is allowed to read content, because the content is visible to friends and the user is the author.");
        return true;
      }
      Boolean isFriend = retryableFriendshipApiClient.isFriendWith(contentAuthor.getEmail(),
          currentUser.getEmail());

      if (isFriend) {
        LOGGER.info("User with email " + currentUser.getEmail()
            + " is allowed to read content, because the content is visible to friends and the user is a friend of the author.");
        return true;
      } else {
        LOGGER.error("User with email " + currentUser.getEmail()
            + " is not allowed to read content, because the content is visible to friends and the user is not a friend of the author. The current user is also not the author of the content.");
        return false;
      }

    }
    throw new IllegalArgumentException("Visibility not supported");
  }

}
