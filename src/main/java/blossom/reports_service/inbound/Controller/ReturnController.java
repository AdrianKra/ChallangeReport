package blossom.reports_service.inbound.Controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blossom.reports_service.inbound.DTOs.ChallengeReportDTO;
import blossom.reports_service.inbound.DTOs.ChallengeSummaryDTO;
import blossom.reports_service.inbound.Security.JwtValidator;
import blossom.reports_service.model.Quote;
import blossom.reports_service.model.Entities.ChallengeReport;
import blossom.reports_service.model.Entities.ChallengeSummary;
import blossom.reports_service.model.Exceptions.InvalidException;
import blossom.reports_service.model.Services.ReportsService;
import blossom.reports_service.model.Services.RetryableServiceClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Controller class for handling REST requests related to reports
 * 
 * @RestController - Indicates that this class is a controller class for REST
 *                 requests
 * @RequestMapping - Annotation for mapping web requests onto methods in request
 */
@RestController
@RequestMapping("/rest/report")
public class ReturnController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReportsService.class);
  private ReportsService reportsService;
  private RetryableServiceClient quotesService;
  private JwtValidator jwtValidator;

  /**
   * API key for the quotes service
   * 
   * @Value - Annotation for injecting values from properties file
   */
  @Value("${api.ninjas.key}")
  private String apiKey;

  @Autowired
  public ReturnController(ReportsService reportsService, RetryableServiceClient quotesService,
      JwtValidator jwtValidator) {
    this.reportsService = reportsService;
    this.quotesService = quotesService;
    this.jwtValidator = jwtValidator;
  }

  /**
   * Get challenge reports by userId
   * 
   * @param Authorization - JWT token
   * @return List<ChallengeReportDTO> - List of challenge reports
   * 
   */
  @GetMapping("/list")
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

  /**
   * Get total progress by userId and challengeReportId
   * 
   * @param Authorization - JWT token
   * @param userId        - User ID
   */
  @GetMapping("/progress/{challengeId}")
  @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
  public double getTotalProgress(@RequestHeader String Authorization, @PathVariable Long challengeReportId) {

    String userEmail = jwtValidator.getUserEmail(Authorization.substring(7));
    LOGGER.info("Getting total progress for user with email: {} and challengeReportId: {}", userEmail,
        challengeReportId);

    return reportsService.getTotalProgress(userEmail, challengeReportId);
  }

  /**
   * 
   * @param Authorization - JWT token
   * @param userId        - User ID
   * @param challengeId   - Challenge ID
   * @return double - Average daily progress
   * 
   */
  @GetMapping("/average{challengeId}")
  @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
  public double getAverageDailyProgress(@RequestHeader String Authorization, @PathVariable Long challengeId) {

    String userEmail = jwtValidator.getUserEmail(Authorization.substring(7));
    LOGGER.info("Getting average daily progress for user with email: {} and challengeId: {}", userEmail, challengeId);

    return reportsService.getAverageDailyProgress(userEmail, challengeId);
  }

  /**
   * Get challenge summary by userId
   * 
   * @param Authorization - JWT token
   * @return ChallengeSummaryDTO - Challenge summary
   * 
   */
  @GetMapping("/summary")
  @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
  public ChallengeSummaryDTO getChallengeSummary(@RequestHeader String Authorization) {

    String userEmail = jwtValidator.getUserEmail(Authorization.substring(7));
    LOGGER.info("Getting challenge summary for user with email: {}", userEmail);

    ChallengeSummary summary = reportsService.getChallengeSummary(userEmail);

    // map the summary to DTO
    ModelMapper modelMapper = new ModelMapper();
    ChallengeSummaryDTO summaryDTO = modelMapper.map(summary, ChallengeSummaryDTO.class);

    return summaryDTO;
  }

  /**
   * Get quotes by category
   * 
   * @param Authorization - JWT token
   * @param category      - Quote category
   * @return Quote[] - Array of quotes
   * 
   */
  @GetMapping("/quote/{category}")
  public Quote[] getQuotes(@PathVariable String category) {

    LOGGER.info("Getting quotes for category: {}", category);
    if (apiKey == null || apiKey.isEmpty()) {
      LOGGER.error("API key is not configured");
      throw new InvalidException("API key is not configured");
    }
    return quotesService.getQuotes(category);
  }
}
