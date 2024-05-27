package blossom.reports_service.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.*;

@Entity
public class ChallengeReport {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "challenge_FK")
  private Challenge challenge;

  // fk of user
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_FK")
  private User user;

  private Date startDate;
  private Date endDate;
  private String description;
  private List<Double> progress;
  private List<String> timestamps;

  @Enumerated(EnumType.STRING)
  private ChallengeStatus status;

  public ChallengeReport() {
    this.status = ChallengeStatus.OPEN;
  }

  @Autowired
  public ChallengeReport(Challenge challenge, User user, Date startDate, String description) {

    this.user = user;
    this.challenge = challenge;
    this.startDate = startDate;
    this.endDate = null;
    this.description = description;
    this.progress = new ArrayList<>();
    this.progress.add(0.0);
    this.timestamps = new ArrayList<>();
    this.timestamps.add("");
    this.status = ChallengeStatus.OPEN;
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

  public Challenge getChallenge() {
    return challenge;
  }

  public void setChallenge(Challenge challenge) {
    this.challenge = challenge;
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

  public List<Double> getProgress() {
    return progress;
  }

  public void setProgress(List<Double> progress) {
    this.progress = progress;
  }

  public void addProgress(Double progress) {
    this.progress.add(progress);
  }

  public List<String> getTimestamp() {
    return timestamps;
  }

  public void setTimestamp(List<String> timestamps) {
    this.timestamps = timestamps;
  }

  public void addTimestamp(String timestamp) {
    this.timestamps.add(timestamp);
  }

  public ChallengeStatus getStatus() {
    return status;
  }

  public void setStatus(ChallengeStatus status) {
    this.status = status;
  }

}
