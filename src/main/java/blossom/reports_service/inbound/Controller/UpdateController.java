package blossom.reports_service.inbound.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import blossom.reports_service.inbound.ReportDTO;
import blossom.reports_service.model.Entities.ChallengeReport;
import blossom.reports_service.model.Services.ReportsService;

@RestController
@RequestMapping("/rest/call")
public class UpdateController {

  private ReportsService reportsService;

  @Autowired
  public UpdateController(ReportsService reportsService) {
    this.reportsService = reportsService;
  }

  @PostMapping("/createChallengeReport")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<ChallengeReport> createChallengeReport(@RequestBody ReportDTO dto) {
    ChallengeReport challengeReport = reportsService.createChallengeReport(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(challengeReport);
  }

  @PutMapping("/updateChallengeReport")
  @ResponseStatus(value = HttpStatus.OK)
  public ChallengeReport updateReport(@RequestBody ReportDTO dto) {
    return reportsService.updateChallengeReport(dto);
  }

  @DeleteMapping("/deleteChallengeReport/{challengeId}")
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteReport(@PathVariable Long challengeId) {
    reportsService.deleteChallengeReport(challengeId);
  }
}