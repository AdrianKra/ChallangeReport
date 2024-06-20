package blossom.reports_service.inbound.Controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;

import blossom.reports_service.inbound.DTOs.ChallengeReportDTO;
import blossom.reports_service.inbound.Security.JwtValidator;
import blossom.reports_service.model.Entities.ChallengeReport;
import blossom.reports_service.model.Services.ReportsService;

/**
 * Controller class for handling REST requests related to updates
 * 
 * @RestController - Indicates that this class is a controller class for REST
 *                 requests
 * @RequestMapping - Annotation for mapping web requests onto methods in request
 */
@RestController
@RequestMapping("/rest/call")
public class UpdateController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReportsService.class);
  private ReportsService reportsService;
  private JwtValidator jwtValidator;

  @Autowired
  public UpdateController(ReportsService reportsService, JwtValidator jwtValidator) {
    this.reportsService = reportsService;
    this.jwtValidator = jwtValidator;
  }

  /**
   * 
   * @param Authorization
   * @param challengeId
   * @return ResponseEntity<ChallengeReportDTO>
   */
  @PostMapping("/createChallengeReport/{challengeId}/{userId}")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
  public ResponseEntity<ChallengeReportDTO> createChallengeReport(@RequestHeader String Authorization,
      @PathVariable Long challengeId) {

    String userEmail = jwtValidator.getUserEmail(Authorization.substring(7));
    LOGGER.info("Creating ChallengeReport for user with email: {}", userEmail);

    ChallengeReport challengeReport = reportsService.createChallengeReport(userEmail, challengeId);

    ModelMapper modelMapper = new ModelMapper();
    ChallengeReportDTO challengeReportDTO = modelMapper.map(challengeReport, ChallengeReportDTO.class);

    return ResponseEntity.status(HttpStatus.CREATED).body(challengeReportDTO);
  }

  /**
   * 
   * @param challengeId
   * @return ResponseEntity<ChallengeReportDTO>
   */
  @DeleteMapping("/deleteChallengeReport/{challengeReportId}")
  @ResponseStatus(value = HttpStatus.OK)
  @PreAuthorize("hasAuthority('ADMIN')")
  public void deleteReport(@RequestHeader String Authorization, @PathVariable Long challengeReportId) {

    String userEmail = jwtValidator.getUserEmail(Authorization.substring(7));
    LOGGER.info("Deleting ChallengeReport for user with email: {} and challengeId: {}", userEmail, challengeReportId);

    reportsService.deleteChallengeReport(userEmail, challengeReportId);
  }
}
