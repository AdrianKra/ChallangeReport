package blossom.reports_service.inbound.DTOs;

import java.util.Date;

public class ChallengeReportDTO {
  private Long id;
  private Long challengeId;
  private Long userId;
  private String report;
  private Date reportDate;

  public ChallengeReportDTO() {
  }

  public ChallengeReportDTO(Long id, Long challengeId, Long userId, String report, Date reportDate) {
    this.id = id;
    this.challengeId = challengeId;
    this.userId = userId;
    this.report = report;
    this.reportDate = reportDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public String getReport() {
    return report;
  }

  public void setReport(String report) {
    this.report = report;
  }

  public Date getReportDate() {
    return reportDate;
  }

  public void setReportDate(Date reportDate) {
    this.reportDate = reportDate;
  }

  @Override
  public String toString() {
    return "ChallengeReportDTO{" +
        "id=" + id +
        ", challengeId=" + challengeId +
        ", userId=" + userId +
        ", report='" + report + '\'' +
        ", reportDate=" + reportDate +
        '}';
  }
}
