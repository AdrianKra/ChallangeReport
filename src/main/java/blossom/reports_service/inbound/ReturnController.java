package blossom.reports_service.inbound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blossom.reports_service.model.ChallengeReport;
import blossom.reports_service.model.ChallengeSummary;
import blossom.reports_service.model.Quote;
import blossom.reports_service.model.ReportsService;
import blossom.reports_service.model.RetryableServiceClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/rest/report")
public class ReturnController {

  private ReportsService reportsService;
  private RetryableServiceClient quotesService;

  @Value("${api.ninjas.key}")
  private String apiKey;

  @Autowired
  public ReturnController(ReportsService reportsService, RetryableServiceClient quotesService) {
    this.reportsService = reportsService;
    this.quotesService = quotesService;
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

  // get feign client quote
  @GetMapping("/quote/{category}")
  public Quote[] getQuotes(@PathVariable String category) {
    if (apiKey == null || apiKey.isEmpty()) {
      throw new IllegalArgumentException("API key is not configured");
    }
    return quotesService.getQuotes(category);
  }
}
