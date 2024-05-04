package blossom.reports_service.model;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

  private String name;
  private String description;
  private ActivityStatus status;

  public ActivitySummary() {
  }

  public ActivitySummary(ArrayList<Activity> activity, String name, String description) {
    this.activity = activity;
    this.name = name;
    this.description = description;
    this.status = ActivityStatus.OPEN;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ActivityStatus getStatus() {
    return status;
  }

  public void setStatus(ActivityStatus status) {
    this.status = status;
  }
}