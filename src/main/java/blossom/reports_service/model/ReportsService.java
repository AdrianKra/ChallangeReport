package blossom.reports_service.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import blossom.reports_service.inbound.ActivityReportCreateDTO;

@Service
public class ReportsService {

  private final ActivityReportRepository activityReportRepository;
  private final ActivitySummaryRepository activitySummaryRepository;
  private final UserRepository userRepository;
  private final Activity activityRepository;

  @Autowired
  public ReportsService(ActivityReportRepository activityReportRepository,
      ActivitySummaryRepository activitySummaryRepository,
      UserRepository userRepository,
      Activity activityRepository) {

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

    ArrayList<Activity> activities = new ArrayList<Activity>();
    var activitySummary = new ActivitySummary(user.get(), activities);

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
  public ActivityReport createActivityReport(ActivityReportCreateDTO dto, Long userId, Long activityId) {
    // Check if user exists
    Optional<User> optionalUser = userRepository.findById(dto.getUserId());
    if (optionalUser.isEmpty()) {
      throw new UserNotFoundException("User not found");
    }

    // Check if ActivityReport already exists for the given activityId and userId
    boolean reportExists = activityReportRepository.existsByActivityIdAndUserId(dto.getActivityId(), dto.getUserId());
    if (reportExists) {
      throw new ActivityReportAlreadyExistsException("ActivityReport already exists");
    }

    // Assuming you have a method to find Activity by id
    Optional<Activity> optionalActivity = activityRepository.findById(dto.getActivityId());
    if (optionalActivity.isEmpty()) {
      throw new ActivityNotFoundException("Activity not found");
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
    // Set other fields as necessary

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

    // Convert DTO to ActivityReport entity
    ActivityReport activityReport = optionalActivityReport.get();
    activityReport.setActivity(optionalActivity.get());
    activityReport.setName(dto.getName());
    activityReport.setStartDate(dto.getStartDate());
    activityReport.setEndDate(dto.getEndDate());
    activityReport.setCreatedBy(dto.getCreatedBy());
    activityReport.setDescription(dto.getDescription());
    // Set other fields as necessary

    // Save and return the updated ActivityReport
    return activityReportRepository.save(activityReport);
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
