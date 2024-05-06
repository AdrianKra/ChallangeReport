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

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "Shopping_List_FK")
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

  public ActivitySummary(Long userId, ArrayList<Activity> activity, int activityCount, int doneCount, int pendingCount,
      int overdueCount, int consecutiveDays, int longestStreak) {
    this.userId = userId;
    this.activity = activity;
    this.activityCount = activityCount;
    this.doneCount = doneCount;
    this.pendingCount = pendingCount;
    this.overdueCount = overdueCount;
    this.consecutiveDays = consecutiveDays;
    this.longestStreak = longestStreak;
  }

}