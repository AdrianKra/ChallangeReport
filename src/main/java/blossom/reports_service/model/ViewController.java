package blossom.reports_service.model;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/view")
public class ViewController {

  private final ReportsService reportsService;

  @Autowired
  public ViewController(ReportsService reportsService) {
    this.reportsService = reportsService;
  }

  @RequestMapping("/{userId}")
  public ActivityData getActivityData(@PathVariable Long userId) {
    ActivitySummary summary = reportsService.getActivitySummary(userId);
    Iterable<ActivityReport> reports = reportsService.getActivityReports(userId);

    ActivityData activityData = new ActivityData(summary, reports);

    return activityData;
  }
}
