package blossom.reports_service.model;

import jakarta.persistence.*;
import java.util.ArrayList;

@Entity
public class ActivitySummary {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "activity_id", referencedColumnName = "id")
  private ArrayList<Activity> activity;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_FK")
  private User user;
  private Long userId;

  private int activityCount;
  private int doneCount;
  private int pendingCount;
  private int overdueCount;

  private int consecutiveDays;
  private int longestStreak;

  public ActivitySummary() {
    this.activity = new ArrayList<Activity>();
  }

  public ActivitySummary(User user, ArrayList<Activity> activity, int activityCount, int doneCount, int pendingCount,
      int overdueCount, int consecutiveDays, int longestStreak) {
    this.userId = user.getId();
    this.activity = activity;
    this.activityCount = activityCount;
    this.doneCount = doneCount;
    this.pendingCount = pendingCount;
    this.overdueCount = overdueCount;
    this.consecutiveDays = consecutiveDays;
    this.longestStreak = longestStreak;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ArrayList<Activity> getActivity() {
    return activity;
  }

  public void setActivity(ArrayList<Activity> activity) {
    this.activity = activity;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public int getActivityCount() {
    return activityCount;
  }

  public void setActivityCount(int activityCount) {
    this.activityCount = activityCount;
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
    return "ActivitySummary [activity=" + activity + ", activityCount=" + activityCount + ", consecutiveDays="
        + consecutiveDays + ", doneCount=" + doneCount + ", id=" + id + ", longestStreak=" + longestStreak
        + ", overdueCount=" + overdueCount + ", pendingCount=" + pendingCount + ", userId=" + userId + "]";
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ActivitySummary other = (ActivitySummary) obj;
    if (activity == null) {
      if (other.activity != null)
        return false;
    } else if (!activity.equals(other.activity))
      return false;
    if (activityCount != other.activityCount)
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
    if (userId == null) {
      if (other.userId != null)
        return false;
    } else if (!userId.equals(other.userId))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((activity == null) ? 0 : activity.hashCode());
    result = prime * result + activityCount;
    result = prime * result + consecutiveDays;
    result = prime * result + doneCount;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + longestStreak;
    result = prime * result + overdueCount;
    result = prime * result + pendingCount;
    result = prime * result + ((userId == null) ? 0 : userId.hashCode());
    return result;
  }

}