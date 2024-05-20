package blossom.reports_service.inbound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import blossom.reports_service.model.ChallengeReport;
import blossom.reports_service.model.ReportsService;

import org.springframework.web.bind.annotation.PathVariable;

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
  public ChallengeReport createChallengeReport(@RequestBody ReportDTO dto) {
    // create new date specific to the current day
    return reportsService.createChallengeReport(dto);
  }

  @PutMapping("updateChallengeReport/{id}")
  public ChallengeReport putMethodName(@PathVariable Long id, @RequestBody ReportDTO dto) {
    return reportsService.updateChallengeReport(id, dto);
  }

  @DeleteMapping("deleteChallengeReport/{id}")
  public void deleteMethodName(@PathVariable Long id) {
    reportsService.deleteChallengeReport(id);
  }
}
