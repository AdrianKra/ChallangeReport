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

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ChallengeSummary other = (ChallengeSummary) obj;
    if (challengeCount != other.challengeCount)
      return false;
    if (consecutiveDays != other.consecutiveDays)
      return false;
    if (doneCount != other.doneCount)
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (longestStreak != other.longestStreak)
      return false;
    if (overdueCount != other.overdueCount)
      return false;
    if (pendingCount != other.pendingCount)
      return false;
    if (user == null) {
      if (other.user != null)
        return false;
    } else if (!user.equals(other.user))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + challengeCount;
    result = prime * result + consecutiveDays;
    result = prime * result + doneCount;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + longestStreak;
    result = prime * result + overdueCount;
    result = prime * result + pendingCount;
    result = prime * result + ((user == null) ? 0 : user.hashCode());
    return result;
  }

}