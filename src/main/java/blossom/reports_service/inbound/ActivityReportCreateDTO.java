package blossom.reports_service.inbound;

import java.util.Date;

import blossom.reports_service.model.ActivityStatus;

public class ActivityReportCreateDTO {
  private Long activityId;
  private Long userId;
  private String name;
  private Date startDate;
  private Date endDate;
  private String createdBy;
  private String description;
  private ActivityStatus status;

  // Standard-Konstruktor
  public ActivityReportCreateDTO() {
  }

  // Konstruktor mit allen Feldern
  public ActivityReportCreateDTO(Long activityId, Long userId, String name, Date startDate, Date endDate,
      String createdBy, String description) {

    this.activityId = activityId;
    this.userId = userId;
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;
    this.createdBy = createdBy;
    this.description = description;
  }

  // Getter und Setter
  public Long getActivityId() {
    return activityId;
  }

  public void setActivityId(Long activityId) {
    this.activityId = activityId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
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