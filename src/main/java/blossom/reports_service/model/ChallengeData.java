package blossom.reports_service.model;

import org.springframework.beans.factory.annotation.Autowired;

public class ChallengeData {
  private ChallengeSummary challengeSummary;
  private Iterable<ChallengeReport> challengeReports;

  public ChallengeData() {
  }

  @Autowired
  public ChallengeData(ChallengeSummary summary, Iterable<ChallengeReport> reports) {
    this.challengeSummary = summary;
    this.challengeReports = reports;
  }

  // getters and setters
  public ChallengeSummary getChallengeSummary() {
    return challengeSummary;
  }

  public void setChallengeSummary(ChallengeSummary challengeSummary) {
    this.challengeSummary = challengeSummary;
  }

  public Iterable<ChallengeReport> getChallengeReports() {
    return challengeReports;
  }

  public void setChallengeReports(Iterable<ChallengeReport> challengeReports) {
    this.challengeReports = challengeReports;
  }
}
