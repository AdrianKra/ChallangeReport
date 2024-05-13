package blossom.reports_service.model;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/setup")
public class SetupController {

  private final ReportsService reportsService;

  @Autowired
  public SetupController(ReportsService reportsService) {
    this.reportsService = reportsService;
  }

  @RequestMapping("/createActivitySummary/{userId}/{date}")
  public ActivitySummary createActivitySummary(@PathVariable Long userId, @PathVariable Date date) {
    return reportsService.createActivitySummary(userId, date);
  }

}