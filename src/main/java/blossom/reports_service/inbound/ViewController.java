package blossom.reports_service.inbound;

import org.springframework.web.bind.annotation.RestController;

import blossom.reports_service.model.ChallengeData;
import blossom.reports_service.model.ChallengeReport;
import blossom.reports_service.model.ChallengeSummary;
import blossom.reports_service.model.ReportsService;
import blossom.reports_service.model.UserNotFoundException;
import blossom.reports_service.model.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/view")
public class ViewController {

  private ReportsService reportsService;
  private UserRepository userRepository = null;

  @Autowired
  public ViewController(ReportsService reportsService) {
    this.reportsService = reportsService;
    this.userRepository = userRepository;
  }

  @GetMapping("/{userId}")
  public ChallengeData getChallengeData(@PathVariable Long userId) {
    if (userRepository.findById(userId).isEmpty()) {
      throw new UserNotFoundException("User not found");
    }

    ChallengeSummary summary = reportsService.getChallengeSummary(userId);
    Iterable<ChallengeReport> reports = reportsService.getChallengeReports(userId);

    ChallengeData challengeData = new ChallengeData(summary, reports);

    return challengeData;
  }
}
