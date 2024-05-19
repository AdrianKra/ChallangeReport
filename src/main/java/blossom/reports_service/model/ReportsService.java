package blossom.reports_service.model;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import blossom.reports_service.inbound.ActivityReportCreateDTO;

@Service
public class ReportsService {

  private final ActivityReportRepository activityReportRepository;
  private final ActivitySummaryRepository activitySummaryRepository;
  private final UserRepository userRepository;
  private final ActivityRepository activityRepository;

  @Autowired
  public ReportsService(ActivityReportRepository activityReportRepository,
      ActivitySummaryRepository activitySummaryRepository,
      UserRepository userRepository,
      ActivityRepository activityRepository) {

    this.activityReportRepository = activityReportRepository;
    this.activitySummaryRepository = activitySummaryRepository;
    this.userRepository = userRepository;
    this.activityRepository = activityRepository;
  }

  // create activitySummary for a new user
  public ActivitySummary createActivitySummary(Long userId, Date date) {
    var user = userRepository.findById(userId);

    if (user.isEmpty()) {
      throw new UserNotFoundException("User not found");
    }

    var activitySummary = new ActivitySummary(user.get());

    activitySummaryRepository.save(activitySummary);
    return activitySummary;
  }

  // get the activitySummary for a User
  public ActivitySummary getActivitySummary(Long userId) {
    var userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) {
      throw new UserNotFoundException("User not found");
    }

    var activitySummary = activitySummaryRepository.findByUser(userOptional.get());
    return activitySummary.get();
  }

  // create activityReport for a new activity
  public ActivityReport createActivityReport(ActivityReportCreateDTO dto) {
    // Check if user exists
    Optional<User> optionalUser = userRepository.findById(dto.getUserId());
    if (optionalUser.isEmpty()) {
      throw new UserNotFoundException("User not found");
    }

    Optional<Activity> optionalActivity = activityRepository.findById(dto.getActivityId());
    if (optionalActivity.isEmpty()) {
      throw new ActivityNotFoundException("Activity not found");
    }

    // Check if ActivityReport already exists for the given activityId and userId
    boolean reportExists = activityReportRepository.existsByActivityIdAndUserId(dto.getActivityId(), dto.getUserId());
    if (reportExists) {
      throw new ActivityReportAlreadyExistsException("ActivityReport already exists");
    }

    // Convert DTO to ActivityReport entity
    ActivityReport activityReport = new ActivityReport();
    activityReport.setActivity(optionalActivity.get());
    activityReport.setUserId(optionalUser.get().getId());
    activityReport.setName(dto.getName());
    activityReport.setStartDate(dto.getStartDate());
    activityReport.setEndDate(dto.getEndDate());
    activityReport.setCreatedBy(dto.getCreatedBy());
    activityReport.setDescription(dto.getDescription());

    var activitySummary = activitySummaryRepository.findByUser(optionalUser.get()).get();
    activitySummary.setActivityCount(activitySummary.getActivityCount() + 1);
    activitySummary.setPendingCount(activitySummary.getPendingCount() + 1);

    // Save and return the new ActivityReport
    return activityReportRepository.save(activityReport);
  }

  // get all activityReports for a user
  public Iterable<ActivityReport> getActivityReports(Long userId) {
    var userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) {
      throw new UserNotFoundException("User not found");
    }

    return activityReportRepository.findAllByUser(userOptional.get());
  }

  // update an activityReport
  public ActivityReport updateActivityReport(Long activityReportId, ActivityReportCreateDTO dto) {
    // Check if ActivityReport exists
    Optional<ActivityReport> optionalActivityReport = activityReportRepository.findById(activityReportId);
    if (optionalActivityReport.isEmpty()) {
      throw new ActivityReportNotFoundException("ActivityReport not found");
    }

    // Check if Activity exists
    Optional<Activity> optionalActivity = activityRepository.findById(dto.getActivityId());
    if (optionalActivity.isEmpty()) {
      throw new ActivityNotFoundException("Activity not found");
    }

    var userOptional = userRepository.findById(dto.getUserId());
    if (userOptional.isEmpty()) {
      throw new UserNotFoundException("User not found");
    }

    // Convert DTO to ActivityReport entity
    ActivityReport activityReport = optionalActivityReport.get();
    // * */
    activityReport.setActivity(optionalActivity.get());
    activityReport.setName(dto.getName());
    activityReport.setStartDate(dto.getStartDate());
    activityReport.setEndDate(dto.getEndDate());
    activityReport.setCreatedBy(dto.getCreatedBy());
    activityReport.setDescription(dto.getDescription());
    activityReport.setStatus(dto.getStatus());

    // update activitySummary attributes
    Date date = new Date();

    // done
    var activitySummary = activitySummaryRepository.findByUser(userOptional.get()).get();
    if (activityReport.getStatus().equals(ActivityStatus.DONE)) {
      activitySummary.setDoneCount(activitySummary.getDoneCount() + 1);
      activitySummary.setPendingCount(activitySummary.getPendingCount() - 1);
      // update OverdueCount if the activity was overdue
      if (date.after(activityReport.getEndDate())) {
        activitySummary.setOverdueCount(activitySummary.getOverdueCount() - 1);
      }
    }
    // overdue
    if (date.after(activityReport.getEndDate())) {
      activitySummary.setOverdueCount(activitySummary.getOverdueCount() + 1);
      activityReport.setStatus(ActivityStatus.OVERDUE);
    }
    // update lastActive and streaks
    activitySummary.setConsecutiveDays(activitySummary.getConsecutiveDays() + 1);
    if (activitySummary.getConsecutiveDays() > activitySummary.getLongestStreak()) {
      activitySummary.setLongestStreak(activitySummary.getConsecutiveDays());
    }
    activitySummary.setLastActive(date);

    // Save and return the updated ActivityReport
    return activityReportRepository.save(activityReport);
  }

  // delete an activityReport
  public void deleteActivityReport(Long activityReportId) {
    // Check if ActivityReport exists
    Optional<ActivityReport> optionalActivityReport = activityReportRepository.findById(activityReportId);
    if (optionalActivityReport.isEmpty()) {
      throw new ActivityReportNotFoundException("ActivityReport not found");
    }

    // Check if User exists
    Optional<User> optionalUser = userRepository.findById(optionalActivityReport.get().getUserId());
    if (optionalUser.isEmpty()) {
      throw new UserNotFoundException("User not found");
    }

    // update activitySummary attributes
    var activitySummary = activitySummaryRepository.findByUser(optionalUser.get()).get();
    // if (optionalActivityReport.get().getStatus().equals(ActivityStatus.DONE)) {
    // activitySummary.setDoneCount(activitySummary.getDoneCount() - 1);
    // } else {
    activitySummary.setPendingCount(activitySummary.getPendingCount() - 1);
    // }
    if (optionalActivityReport.get().getStatus().equals(ActivityStatus.OVERDUE)) {
      activitySummary.setOverdueCount(activitySummary.getOverdueCount() - 1);
    }
    activitySummary.setActivityCount(activitySummary.getActivityCount() - 1);

    // delete the ActivityReport
    activityReportRepository.deleteById(activityReportId);
  }

  // // sort activityReports by startDate
  // public Iterable<ActivityReport> sortActivityReportsByStartDate(Long userId) {
  // var userOptional = userRepository.findById(userId);
  // if (userOptional.isEmpty()) {
  // throw new UserNotFoundException("User not found");
  // }

  // Iterable<ActivityReport> activityReports =
  // activityReportRepository.findAllByUser(userOptional.get());

  // List<ActivityReport> activityList = toList(activityReports);
  // activityList.sort(Comparator.comparing(ActivityReport::getStartDate));

  // return activityList;
  // }

  // // turn generic Iterable into List
  // public <T> List<T> toList(Iterable<T> iterable) {
  // List<T> list = new ArrayList<T>();
  // iterable.forEach(list::add);
  // return list;
  // }
}
