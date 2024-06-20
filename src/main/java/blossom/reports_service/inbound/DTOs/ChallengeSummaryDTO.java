package blossom.reports_service.inbound.DTOs;

import java.util.Date;

import blossom.reports_service.model.Entities.ChallengeSummary;
import blossom.reports_service.model.Entities.User;
import jakarta.validation.constraints.NotNull;

public class ChallengeSummaryDTO {

  private Long id;
  private User user;
  private Date lastActive;
  private int challengeCount;
  private int doneCount;
  private int pendingCount;
  private int overdueCount;
  private int consecutiveDays;
  private int longestStreak;
  private int version;

  /**
   * Default Constructor for ChallengeSummaryDTO
   */
  public ChallengeSummaryDTO() {
  }

  /**
   * Parameterized Constructor for ChallengeSummaryDTO
   */
  public ChallengeSummaryDTO(Long id, User user, Date lastActive, int challengeCount, int doneCount, int pendingCount,
      int overdueCount, int consecutiveDays, int longestStreak, int version) {
    this.id = id;
    this.user = user;
    this.lastActive = lastActive;
    this.challengeCount = challengeCount;
    this.doneCount = doneCount;
    this.pendingCount = pendingCount;
    this.overdueCount = overdueCount;
    this.consecutiveDays = consecutiveDays;
    this.longestStreak = longestStreak;
    this.version = version;
  }

  /**
   * Constructor for ChallengeSummaryDTO from ChallengeSummary
   */
  public ChallengeSummaryDTO(ChallengeSummary challengeSummary) {
    this.id = challengeSummary.getId();
    this.user = challengeSummary.getUser();
    this.lastActive = challengeSummary.getLastActive();
    this.challengeCount = challengeSummary.getChallengeCount();
    this.doneCount = challengeSummary.getDoneCount();
    this.pendingCount = challengeSummary.getPendingCount();
    this.overdueCount = challengeSummary.getOverdueCount();
    this.consecutiveDays = challengeSummary.getConsecutiveDays();
    this.longestStreak = challengeSummary.getLongestStreak();
    this.version = challengeSummary.getVersion();
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

  public Date getLastActive() {
    return lastActive;
  }

  public void setLastActive(Date lastActive) {
    this.lastActive = lastActive;
  }

  public int getChallengeCount() {
    return challengeCount;
  }

  public void setChallengeCount(int challengeCount) {
    this.challengeCount = challengeCount;
  }

  public int getDoneCount() {
    return doneCount;
  }

  public void setDoneCount(int doneCount) {
    this.doneCount = doneCount;
  }

  public int getPendingCount() {
    return pendingCount;
  }

  public void setPendingCount(int pendingCount) {
    this.pendingCount = pendingCount;
  }

  public int getOverdueCount() {
    return overdueCount;
  }

  public void setOverdueCount(int overdueCount) {
    this.overdueCount = overdueCount;
  }

  public int getConsecutiveDays() {
    return consecutiveDays;
  }

  public void setConsecutiveDays(int consecutiveDays) {
    this.consecutiveDays = consecutiveDays;
  }

  public int getLongestStreak() {
    return longestStreak;
  }

  public void setLongestStreak(int longestStreak) {
    this.longestStreak = longestStreak;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return "ChallengeSummaryDTO [challengeCount=" + challengeCount + ", consecutiveDays=" + consecutiveDays
        + ", doneCount=" + doneCount + ", id=" + id + ", lastActive=" + lastActive + ", longestStreak="
        + longestStreak + ", overdueCount=" + overdueCount + ", pendingCount=" + pendingCount + ", user=" + user
        + ", version=" + version + "]";
  }
}
