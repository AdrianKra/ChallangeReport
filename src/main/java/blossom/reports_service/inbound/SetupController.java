package blossom.reports_service.inbound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import blossom.reports_service.model.ChallengeSummary;
import blossom.reports_service.model.ReportsService;

@Controller
@RequestMapping("/setup")
public class SetupController {

  private ReportsService reportsService;

  @Autowired
  public SetupController(ReportsService reportsService) {
    this.reportsService = reportsService;
  }

  @PostMapping("/createChallengeSummary/{userId}")
  public ChallengeSummary createChallengeSummary(@PathVariable Long userId) {
    return reportsService.createChallengeSummary(userId);
  }

}
