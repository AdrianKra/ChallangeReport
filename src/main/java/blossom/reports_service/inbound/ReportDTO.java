package blossom.reports_service.inbound;

import java.util.Date;

import blossom.reports_service.model.ChallengeStatus;

public class ReportDTO {
  private Long challengeId;
  private Long userId;
  private Date startDate;
  private Date endDate;
  private String description;
  private ChallengeStatus status;

  // Standard-Konstruktor
  public ReportDTO() {
  }

  // Konstruktor mit allen Feldern
  public ReportDTO(Long challengeId, Long userId, Date startDate, Date endDate, String description,
      ChallengeStatus status) {

    this.challengeId = challengeId;
    this.userId = userId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.description = description;
    this.status = status;
  }

  // Getter und Setter
  public Long getChallengeId() {
    return challengeId;
  }

  public void setChallengeId(Long challengeId) {
    this.challengeId = challengeId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ChallengeStatus getStatus() {
    return status;
  }

  public void setStatus(ChallengeStatus status) {
    this.status = status;
  }
}