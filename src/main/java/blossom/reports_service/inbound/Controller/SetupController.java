package blossom.reports_service.inbound.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import blossom.reports_service.inbound.Security.JwtValidator;
import blossom.reports_service.model.Services.ReportsService;

/**
 * Controller class for handling REST requests related to setup
 * 
 * @RestController - Indicates that this class is a controller class for REST
 *                 requests
 * @RequestMapping - Annotation for mapping web requests onto methods in
 *                 request-
 */
@RestController
@RequestMapping("/rest/setup")
public class SetupController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReportsService.class);
  private final ReportsService reportsService;

  private JwtValidator jwtValidator;

  @Autowired
  public SetupController(ReportsService reportsService, JwtValidator jwtValidator) {
    this.reportsService = reportsService;
    this.jwtValidator = jwtValidator;
  }

  /**
   * Create ChallengeSummary for the user with the given email
   * 
   * @param Authorization
   * @param userEmail
   */
  @PostMapping("/createSummary")
  @ResponseStatus(value = HttpStatus.OK)
  @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
  public void createChallengeSummary(@RequestHeader String Authorization) {

    String userEmail = jwtValidator.getUserEmail(Authorization.substring(7));
    LOGGER.info("Creating ChallengeSummary for user with Email: {}", userEmail);

    reportsService.createChallengeSummary(userEmail);
  }
}
