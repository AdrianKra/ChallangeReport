package blossom.reports_service.inbound.DTOs;

import java.util.Date;

public class ChallengeSummaryDTO {
  private Long id;
  private Long challengeId;
  private String summary;
  private Date summaryDate;

  public ChallengeSummaryDTO() {
  }

  public ChallengeSummaryDTO(Long id, Long challengeId, String summary, Date summaryDate) {
    this.id = id;
    this.challengeId = challengeId;
    this.summary = summary;
    this.summaryDate = summaryDate;
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

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public Date getSummaryDate() {
    return summaryDate;
  }

  public void setSummaryDate(Date summaryDate) {
    this.summaryDate = summaryDate;
  }

  @Override
  public String toString() {
    return "ChallengeSummaryDTO{" +
        "id=" + id +
        ", challengeId=" + challengeId +
        ", summary='" + summary + '\'' +
        ", summaryDate=" + summaryDate +
        '}';
  }
}
