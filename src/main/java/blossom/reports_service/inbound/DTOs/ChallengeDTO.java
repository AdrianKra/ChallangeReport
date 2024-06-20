package blossom.reports_service.inbound.DTOs;

import java.util.Date;

import blossom.reports_service.model.Entities.Challenge;
import blossom.reports_service.model.Entities.User;
import blossom.reports_service.model.Enums.Unit;
import blossom.reports_service.model.Enums.Visibility;

/**
 * Data Transfer Object for Challenge
 */
public class ChallengeDTO {

  private Long id;

  private String title;
  private String description;
  private Unit unit;
  private double targetProgress;
  private Date deadline;
  private Integer scoreReward;
  private Integer scorePenalty;
  private User user;
  private Visibility challengeVisibility;
  private int version;

  /**
   * Default Constructor for ChallengeDTO
   */
  public ChallengeDTO() {
  }

  /**
   * Parameterized Constructor for ChallengeDTO
   */
  public ChallengeDTO(Long id, String title, String description, Unit unit, double targetProgress, Date deadline,
      Integer scoreReward, Integer scorePenalty, User user, Visibility challengeVisibility, int version) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.unit = unit;
    this.targetProgress = targetProgress;
    this.deadline = deadline;
    this.scoreReward = scoreReward;
    this.scorePenalty = scorePenalty;
    this.user = user;
    this.challengeVisibility = challengeVisibility;
    this.version = version;
  }

  /**
   * Constructor for ChallengeDTO from Challenge
   */
  public ChallengeDTO(Challenge challenge) {
    this.id = challenge.getId();
    this.title = challenge.getTitle();
    this.description = challenge.getDescription();
    this.unit = challenge.getUnit();
    this.targetProgress = challenge.getTargetProgress();
    this.deadline = challenge.getDeadline();
    this.scoreReward = challenge.getScoreReward();
    this.scorePenalty = challenge.getScorePenalty();
    this.user = challenge.getUser();
    this.challengeVisibility = challenge.getChallengeVisibility();
    this.version = challenge.getVersion();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Unit getUnit() {
    return unit;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }

  public double getTargetProgress() {
    return targetProgress;
  }

  public void setTargetProgress(double targetProgress) {
    this.targetProgress = targetProgress;
  }

  public Date getDeadline() {
    return deadline;
  }

  public void setDeadline(Date deadline) {
    this.deadline = deadline;
  }

  public Integer getScoreReward() {
    return scoreReward;
  }

  public void setScoreReward(Integer scoreReward) {
    this.scoreReward = scoreReward;
  }

  public Integer getScorePenalty() {
    return scorePenalty;
  }

  public void setScorePenalty(Integer scorePenalty) {
    this.scorePenalty = scorePenalty;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Visibility getChallengeVisibility() {
    return challengeVisibility;
  }

  public void setChallengeVisibility(Visibility challengeVisibility) {
    this.challengeVisibility = challengeVisibility;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return "ChallengeDTO [challengeVisibility=" + challengeVisibility + ", deadline=" + deadline + ", description="
        + description + ", id=" + id + ", scorePenalty=" + scorePenalty + ", scoreReward=" + scoreReward + ", title="
        + title + ", unit=" + unit + ", user=" + user + ", version=" + version + "]";
  }
}
