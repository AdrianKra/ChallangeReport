package blossom.reports_service.inbound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blossom.reports_service.model.ActivityReport;
import blossom.reports_service.model.ActivitySummary;
import blossom.reports_service.model.ReportsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/report")
public class AutomaticController {

  ReportsService reportsService;

  @Autowired
  public AutomaticController(ReportsService reportsService) {
    this.reportsService = reportsService;
  }

  // get all activity reports for a user
  @GetMapping("/list/{id}")
  public Iterable<ActivityReport> getActivityReports(@PathVariable Long id) {
    Iterable<ActivityReport> reports = reportsService.getActivityReports(id);
    return reports;
  }

  // get activity summary by userId
  @GetMapping("/summary/{id}")
  public ActivitySummary getActivitySummary(@PathVariable Long id) {
    ActivitySummary summary = reportsService.getActivitySummary(id);
    return summary;
  }
}
