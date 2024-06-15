package blossom.reports_service.inbound.DTOs;

import blossom.reports_service.model.Entities.Challenge;
import blossom.reports_service.model.Entities.ChallengeProgress;
import blossom.reports_service.model.Entities.ChallengeReport;
import blossom.reports_service.model.Entities.User;
import blossom.reports_service.model.Enums.Visibility;

public class ChallengeProgressDTO {

  private Long id;
  private User user;
  private Challenge challenge;
  private ChallengeReport challengeReport;
  private Double currentProgress;
  private Visibility progressVisibility;
  private int version;

  public ChallengeProgressDTO() {
  }

  public ChallengeProgressDTO(User user, Challenge challenge, ChallengeReport challengeReport, Double currentProgress,
      Visibility progressVisibility) {
    this.user = user;
    this.challenge = challenge;
    this.challengeReport = challengeReport;
    this.currentProgress = currentProgress;
    this.progressVisibility = progressVisibility;
  }

  public ChallengeProgressDTO(ChallengeProgress challengeProgress) {
    this.id = challengeProgress.getId();
    this.user = challengeProgress.getUser();
    this.challenge = challengeProgress.getChallenge();
    this.challengeReport = challengeProgress.getChallengeReport();
    this.currentProgress = challengeProgress.getCurrentProgress();
    this.progressVisibility = challengeProgress.getProgressVisibility();
    this.version = challengeProgress.getVersion();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Challenge getChallenge() {
    return challenge;
  }

  public void setChallenge(Challenge challenge) {
    this.challenge = challenge;
  }

  public ChallengeReport getChallengeReport() {
    return challengeReport;
  }

  public void setChallengeReport(ChallengeReport challengeReport) {
    this.challengeReport = challengeReport;
  }

  public Double getCurrentProgress() {
    return currentProgress;
  }

  public void setCurrentProgress(Double currentProgress) {
    this.currentProgress = currentProgress;
  }

  public Visibility getProgressVisibility() {
    return progressVisibility;
  }

  public void setProgressVisibility(Visibility progressVisibility) {
    this.progressVisibility = progressVisibility;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }
}
