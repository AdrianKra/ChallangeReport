package blossom.reports_service.inbound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blossom.reports_service.model.ChallengeReport;
import blossom.reports_service.model.ChallengeSummary;
import blossom.reports_service.model.ReportsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/report")
public class AutomaticController {

  private ReportsService reportsService;

  @Autowired
  public AutomaticController(ReportsService reportsService) {
    this.reportsService = reportsService;
  }

  // get all challange reports for a user
  @GetMapping("/list/{id}")
  public Iterable<ChallengeReport> getChallengeReports(@PathVariable Long id) {
    Iterable<ChallengeReport> reports = reportsService.getChallengeReports(id);
    return reports;
  }

  // get challange summary by userId
  @GetMapping("/summary/{id}")
  public ChallengeSummary getChallengeSummary(@PathVariable Long id) {
    ChallengeSummary summary = reportsService.getChallengeSummary(id);
    return summary;
  }
}
