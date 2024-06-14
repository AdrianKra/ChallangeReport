package blossom.reports_service.model.Entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.ArrayList;

@Entity
public class ChallengeSummary {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_FK")
  private User user;

  private Date lastActive;

  private int dailyChallenges;

  private int challengeCount;
  private int doneCount;
  private int pendingCount;
  private int overdueCount;

  private int consecutiveDays;
  private int longestStreak;

  @Version
  private int version;

  public ChallengeSummary() {
  }

  public ChallengeSummary(User user) {
    this.user = user;
    this.lastActive = new Date();
    this.challengeCount = 0;
    this.doneCount = 0;
    this.pendingCount = 0;
    this.overdueCount = 0;
    this.consecutiveDays = 0;
    this.longestStreak = 0;
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

  @Override
  public String toString() {
    return "ChallengeSummary [challengeCount=" + challengeCount + ", consecutiveDays="
        + consecutiveDays + ", doneCount=" + doneCount + ", id=" + id + ", longestStreak=" + longestStreak
        + ", overdueCount=" + overdueCount + ", pendingCount=" + pendingCount + ", userId=" + user.toString() + "]";
  }

  public void incrementDoneCount() {
    this.doneCount++;
  }

  public void decrementPendingCount() {
    this.pendingCount--;
  }

  public void decrementOverdueCount() {
    this.overdueCount = Math.max(0, this.overdueCount - 1);
  }

  public void incrementOverdueCount() {
    this.overdueCount++;
  }

  public void resetConsecutiveDays() {
    this.consecutiveDays = 0;
  }

  public void incrementConsecutiveDays() {
    this.consecutiveDays++;
  }

  public void updateLongestStreak() {
    this.longestStreak = this.consecutiveDays;
  }

  public void updateLastActive(Date date) {
    this.lastActive = date;
  }

  public void decrementChallengeCount() {
    this.challengeCount--;
  }

  public void decrementDoneCount() {
    this.doneCount--;
  }

}