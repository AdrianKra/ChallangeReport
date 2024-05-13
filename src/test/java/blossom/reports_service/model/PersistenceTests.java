package blossom.reports_service.model;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PersistenceTests {

  @Autowired
  private ActivityRepository activityRepository;

  @Autowired
  private ActivityReportRepository activityReportRepository;

  @Autowired
  private ActivitySummaryRepository activitySummaryRepository;

  @Autowired
  private UserRepository userRepository;

  // Activity Tests
  @Test
  public void ActivityFindById() {
    Optional<ActivityReport> activityReport = activityReportRepository.findById(1L);
    Optional<ActivityReport> activityReport2 = activityReportRepository.findById(2L);
    Optional<ActivityReport> activityReport3 = activityReportRepository.findById(3L);
    assert (activityReport.isPresent());
    assert (activityReport2.isPresent());
    assert (activityReport3.isEmpty());
  }

  @Test
  public void ActivityDeleteById() {
    activityRepository.deleteById(1L);
    Optional<Activity> activityReport = activityRepository.findById(1L);
    assert (activityReport.isEmpty());
  }

  // ActivityReport Tests
  @Test
  public void ReportFindById() {
    Optional<ActivityReport> activityReport = activityReportRepository.findById(1L);
    Optional<ActivityReport> activityReport2 = activityReportRepository.findById(2L);
    Optional<ActivityReport> activityReport3 = activityReportRepository.findById(3L);
    assert (activityReport.isPresent());
    assert (activityReport2.isPresent());
    assert (activityReport3.isEmpty());
  }

  @Test
  public void ReportFindByActivity() {
    Optional<Activity> activity = activityRepository.findById(1L);
    Optional<ActivityReport> activityReport = activityReportRepository.findByActivity(activity.get());
    assert (activityReport.isPresent());
  }

  @Test
  public void ReportFindByStatus() {
    Iterable<ActivityReport> activityReports = activityReportRepository.findAllByStatus(ActivityStatus.OPEN);
    assert (activityReports.iterator().hasNext());
  }

  @Test
  public void ReportDeleteById() {
    activityReportRepository.deleteById(1L);
    Optional<ActivityReport> activityReport = activityReportRepository.findById(1L);
    assert (activityReport.isEmpty());
  }

  @Test
  public void ReportSave() {
    Optional<Activity> activityOptional = activityRepository.findById(3L);
    Optional<User> userOptional = userRepository.findById(3L);

    ActivityReport activityReport = new ActivityReport(activityOptional.get(), userOptional.get(), "Report 3",
        new java.util.Date(), "John Doe", "Report Description");

    activityReportRepository.save(activityReport);

    Optional<ActivityReport> activity = activityReportRepository.findById(activityReport.getId());
    assert (activity.isPresent());
  }
}
