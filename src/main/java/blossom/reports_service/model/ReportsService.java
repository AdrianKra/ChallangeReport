package blossom.reports_service.model;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import blossom.reports_service.inbound.ReportDTO;

@Service
public class ReportsService {

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

  // create challengeSummary for a new user
  public ChallengeSummary createChallengeSummary(Long userId) {
    var user = userRepository.findById(userId);

    if (user.isEmpty()) {
      throw new UserNotFoundException("User not found");
    }

    var challengeSummary = new ChallengeSummary(user.get());

    challengeSummaryRepository.save(challengeSummary);
    return challengeSummary;
  }

  // get the challengeSummary for a User
  public ChallengeSummary getChallengeSummary(Long userId) {
    var userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) {
      throw new UserNotFoundException("User not found");
    }

    var challengeSummary = challengeSummaryRepository.findByUser(userOptional.get());
    return challengeSummary.get();
  }

  // create challengeReport for a new challenge
  public ChallengeReport createChallengeReport(ReportDTO dto) {
    // Check if user exists
    Optional<User> optionalUser = userRepository.findById(dto.getUserId());
    if (optionalUser.isEmpty()) {
      throw new UserNotFoundException("User not found");
    }

    Optional<Challenge> optionalChallenge = challengeRepository.findById(dto.getChallengeId());
    if (optionalChallenge.isEmpty()) {
      throw new ChallengeNotFoundException("Challenge not found");
    }

    // Check if ChallengeReport already exists for the given challengeId and userId
    boolean reportExists = challengeReportRepository.existsByChallengeIdAndUserId(dto.getChallengeId(),
        dto.getUserId());
    if (reportExists) {
      throw new ChallengeReportAlreadyExistsException("ChallengeReport already exists");
    }

    // Convert DTO to ChallengeReport entity
    ChallengeReport challengeReport = new ChallengeReport();
    challengeReport.setChallenge(optionalChallenge.get());
    challengeReport.setUserId(optionalUser.get().getId());
    challengeReport.setName(dto.getName());
    challengeReport.setStartDate(dto.getStartDate());
    challengeReport.setEndDate(dto.getEndDate());
    challengeReport.setCreatedBy(dto.getCreatedBy());
    challengeReport.setDescription(dto.getDescription());

    var challengeSummary = challengeSummaryRepository.findByUser(optionalUser.get()).get();
    challengeSummary.setChallengeCount(challengeSummary.getChallengeCount() + 1);
    challengeSummary.setPendingCount(challengeSummary.getPendingCount() + 1);

    // Save and return the new ChallengeReport
    return challengeReportRepository.save(challengeReport);
  }

  // get all challengeReports for a user
  public Iterable<ChallengeReport> getChallengeReports(Long userId) {
    var userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) {
      throw new UserNotFoundException("User not found");
    }

    return challengeReportRepository.findAllByUser(userOptional.get());
  }

  // update an challengeReport
  public ChallengeReport updateChallengeReport(Long challengeReportId, ReportDTO dto) {
    // Check if ChallengeReport exists
    Optional<ChallengeReport> optionalChallengeReport = challengeReportRepository.findById(challengeReportId);
    if (optionalChallengeReport.isEmpty()) {
      throw new ChallengeReportNotFoundException("ChallengeReport not found");
    }

    // Check if Challenge exists
    Optional<Challenge> optionalChallenge = challengeRepository.findById(dto.getChallengeId());
    if (optionalChallenge.isEmpty()) {
      throw new ChallengeNotFoundException("Challenge not found");
    }

    var userOptional = userRepository.findById(dto.getUserId());
    if (userOptional.isEmpty()) {
      throw new UserNotFoundException("User not found");
    }

    // Convert DTO to ChallengeReport entity
    ChallengeReport challengeReport = optionalChallengeReport.get();
    // * */
    challengeReport.setChallenge(optionalChallenge.get());
    challengeReport.setName(dto.getName());
    challengeReport.setStartDate(dto.getStartDate());
    challengeReport.setEndDate(dto.getEndDate());
    challengeReport.setCreatedBy(dto.getCreatedBy());
    challengeReport.setDescription(dto.getDescription());
    challengeReport.setStatus(dto.getStatus());

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
  public void deleteChallengeReport(Long challengeReportId) {
    // Check if ChallengeReport exists
    Optional<ChallengeReport> optionalChallengeReport = challengeReportRepository.findById(challengeReportId);
    if (optionalChallengeReport.isEmpty()) {
      throw new ChallengeReportNotFoundException("ChallengeReport not found");
    }

    // Check if User exists
    Optional<User> optionalUser = userRepository.findById(optionalChallengeReport.get().getUserId());
    if (optionalUser.isEmpty()) {
      throw new UserNotFoundException("User not found");
    }

    // update challengeSummary attributes
    var challengeSummary = challengeSummaryRepository.findByUser(optionalUser.get()).get();
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

  // // sort challengeReports by startDate
  // public Iterable<ChallengeReport> sortChallengeReportsByStartDate(Long userId)
  // {
  // var userOptional = userRepository.findById(userId);
  // if (userOptional.isEmpty()) {
  // throw new UserNotFoundException("User not found");
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
