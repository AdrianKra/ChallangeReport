package blossom.reports_service.inbound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import blossom.reports_service.model.ChallengeReport;
import blossom.reports_service.model.ReportsService;

@RestController
@RequestMapping("/call")
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

  @PutMapping("/updateChallengeReport/{userId}")
  @ResponseStatus(value = HttpStatus.OK)
  public ChallengeReport updateReport(@PathVariable Long userId, @RequestBody ReportDTO dto) {
    return reportsService.updateChallengeReport(userId, dto);
  }

  @DeleteMapping("/deleteChallengeReport/{userId}")
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteReport(@PathVariable Long userId) {
    reportsService.deleteChallengeReport(userId);
  }
}
