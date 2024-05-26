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
public class ReturnController {

  private ReportsService reportsService;

  @Autowired
  public ReturnController(ReportsService reportsService) {
    this.reportsService = reportsService;
  }

  // get all challange reports for a user
  @GetMapping("/list/{userId}")
  public Iterable<ChallengeReport> getChallengeReports(@PathVariable Long userId) {
    Iterable<ChallengeReport> reports = reportsService.getChallengeReports(userId);
    return reports;
  }

  // get challange summary by userId
  @GetMapping("/summary/{userId}")
  public ChallengeSummary getChallengeSummary(@PathVariable Long userId) {
    ChallengeSummary summary = reportsService.getChallengeSummary(userId);
    return summary;
  }
}
