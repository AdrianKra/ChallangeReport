package blossom.reports_service.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class ActivityData {
  private ActivitySummary activitySummary;
  private Iterable<ActivityReport> activityReports;

  public ActivityData() {
  }

  @Autowired
  public ActivityData(ActivitySummary summary, Iterable<ActivityReport> reports) {
    this.activitySummary = summary;
    this.activityReports = reports;
  }

  // getters and setters
  public ActivitySummary getActivitySummary() {
    return activitySummary;
  }

  public void setActivitySummary(ActivitySummary activitySummary) {
    this.activitySummary = activitySummary;
  }

  public Iterable<ActivityReport> getActivityReports() {
    return activityReports;
  }

  public void setActivityReports(Iterable<ActivityReport> activityReports) {
    this.activityReports = activityReports;
  }
}
