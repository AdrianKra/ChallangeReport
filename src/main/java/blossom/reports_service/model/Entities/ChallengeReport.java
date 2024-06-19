package blossom.reports_service.model.Entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import blossom.reports_service.model.Enums.ChallengeStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class ChallengeReport {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "challenge_FK")
  @NotNull
  private Challenge challenge;

  // fk of user
  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_FK")
  @NotNull
  private User user;

  @OneToMany(mappedBy = "challengeReport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonManagedReference
  private HashMap<Date, ChallengeProgress> progressMap;

  @Temporal(TemporalType.DATE)
  @NotNull
  private Date startDate;

  @Temporal(TemporalType.DATE)
  private Date endDate;

  @Enumerated(EnumType.STRING)
  private ChallengeStatus status;

  @Version
  private int version;

  public ChallengeReport() {
    this.progressMap = new HashMap<>();
    this.startDate = new Date();
    this.status = ChallengeStatus.OPEN;
  }

  public ChallengeReport(User user, Challenge challenge) {
    this.user = user;
    this.progressMap = new HashMap<>();
    this.challenge = challenge;
    this.startDate = new Date();
    this.endDate = null;
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

  public HashMap<Date, ChallengeProgress> getProgressList() {
    return progressMap;
  }

  public void setProgressList(HashMap<Date, ChallengeProgress> progressMap) {
    this.progressMap = progressMap;
  }

  public void addProgress(Date timestamp, ChallengeProgress progress) {
    this.progressMap.put(timestamp, progress);
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

  public ChallengeStatus getStatus() {
    return status;
  }

  public void setStatus(ChallengeStatus status) {
    this.status = status;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return "ChallengeReport {id=" + id + ", user=" + user + ", challenge=" + challenge + ", progressMap="
        + progressMap + ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + ", version="
        + version + "}";
  }

}
