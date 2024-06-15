package blossom.reports_service.inbound.Controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.parser.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blossom.reports_service.inbound.DTOs.ChallengeReportDTO;
import blossom.reports_service.inbound.Security.JwtValidator;
import blossom.reports_service.model.Quote;
import blossom.reports_service.model.Entities.ChallengeReport;
import blossom.reports_service.model.Entities.ChallengeSummary;
import blossom.reports_service.model.Services.ReportsService;
import blossom.reports_service.model.Services.RetryableServiceClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/rest/report")
public class ReturnController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReportsService.class);
  private ReportsService reportsService;
  private RetryableServiceClient quotesService;
  private JwtValidator jwtValidator;

  @Value("${api.ninjas.key}")
  private String apiKey;

  @Autowired
  public ReturnController(ReportsService reportsService, RetryableServiceClient quotesService,
      JwtValidator jwtValidator) {
    this.reportsService = reportsService;
    this.quotesService = quotesService;
    this.jwtValidator = jwtValidator;
  }

  // get all challange reports for a user and return as list of DTOs
  @GetMapping("/list/{userId}")
  @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
  public List<ChallengeReportDTO> getChallengeReports(@RequestHeader String Authorization) {

    String userEmail = jwtValidator.getUserEmail(Authorization.substring(7));
    LOGGER.info("Getting challenge reports for user with email: {}", userEmail);

    Iterable<ChallengeReport> reports = reportsService.getChallengeReports(userEmail);

    List<ChallengeReportDTO> reportList = new ArrayList<>();
    reports.forEach(report -> {
      ChallengeReportDTO dto = new ChallengeReportDTO(report);
      reportList.add(dto);
    });

    return reportList;
  }

  // get challange summary by userId
  @GetMapping("/summary/{userId}")
  @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
  public ChallengeSummary getChallengeSummary(@RequestHeader String Authorization) {

    String userEmail = jwtValidator.getUserEmail(Authorization.substring(7));
    LOGGER.info("Getting challenge summary for user with email: {}", userEmail);

    ChallengeSummary summary = reportsService.getChallengeSummary(userEmail);
    return summary;
  }

  // get feign client quote
  @GetMapping("/quote/{category}")
  @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
  public Quote[] getQuotes(@RequestHeader String Authorization, @PathVariable String category) {

    String userEmail = jwtValidator.getUserEmail(Authorization.substring(7));
    LOGGER.info("Getting quotes for user with email: {}", userEmail);

    if (apiKey == null || apiKey.isEmpty()) {
      LOGGER.error("API key is not configured");
      throw new IllegalArgumentException("API key is not configured");
    }
    return quotesService.getQuotes(category);
  }
}
