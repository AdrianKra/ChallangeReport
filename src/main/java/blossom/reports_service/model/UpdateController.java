package blossom.reports_service.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import blossom.reports_service.inbound.ActivityReportCreateDTO;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/call")
public class UpdateController {

  private final ReportsService reportsService;

  @Autowired
  public UpdateController(ReportsService reportsService) {
    this.reportsService = reportsService;
  }

  @PostMapping("/createActivityReport")
  @ResponseStatus(HttpStatus.CREATED)
  public ActivityReport createActivityReport(@RequestBody ActivityReportCreateDTO dto) {
    // create new date specific to the current day
    return reportsService.createActivityReport(dto);
  }

  @PutMapping("updateActivityReport/{id}")
  public ActivityReport putMethodName(@PathVariable Long id, @RequestBody ActivityReportCreateDTO dto) {
    return reportsService.updateActivityReport(id, dto);
  }

  @DeleteMapping("deleteActivityReport/{id}")
  public void deleteMethodName(@PathVariable Long id) {
    reportsService.deleteActivityReport(id);
  }
}
