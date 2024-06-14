package blossom.reports_service.inbound.DTOs;

import java.util.Date;

public class ChallengeProgressDTO {
  private Long id;
  private Long userId;
  private Long challengeId;
  private int progress;
  private Date startDate;
  private Date endDate;

  public ChallengeProgressDTO() {
  }

  public ChallengeProgressDTO(Long id, Long userId, Long challengeId, int progress, Date startDate, Date endDate) {
    this.id = id;
    this.userId = userId;
    this.challengeId = challengeId;
    this.progress = progress;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getChallengeId() {
    return challengeId;
  }

  public void setChallengeId(Long challengeId) {
    this.challengeId = challengeId;
  }

  public int getProgress() {
    return progress;
  }

  public void setProgress(int progress) {
    this.progress = progress;
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

  @Override
  public String toString() {
    return "ChallengeProgressDTO{" +
        "id=" + id +
        ", userId=" + userId +
        ", challengeId=" + challengeId +
        ", progress=" + progress +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        '}';
  }
}
