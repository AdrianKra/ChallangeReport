package blossom.reports_service.inbound.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import blossom.reports_service.model.Entities.ChallengeSummary;
import blossom.reports_service.model.Services.ReportsService;

@RestController
@RequestMapping("/rest/setup")
public class SetupController {

  private final ReportsService reportsService;

  @Autowired
  public SetupController(ReportsService reportsService) {
    this.reportsService = reportsService;
  }

  @PostMapping("/createSummary/{userId}") // change to email
  @ResponseStatus(value = HttpStatus.OK)
  public void createChallengeSummary(@PathVariable Long userId) {
    reportsService.createChallengeSummary(userId);
  }
}
