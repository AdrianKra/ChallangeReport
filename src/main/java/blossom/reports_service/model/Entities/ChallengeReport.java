package blossom.reports_service.model.Entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import blossom.reports_service.model.Enums.ChallengeStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class ChallengeReport {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull
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
  private List<ChallengeProgress> progressList;

  @Temporal(TemporalType.DATE)
  @NotNull
  private Date startDate;

  @Temporal(TemporalType.DATE)
  private Date endDate;

  @Enumerated(EnumType.STRING)
  @NotNull
  private ChallengeStatus status;

  @Version
  private int version;

  public ChallengeReport() {
    this.progressList = new ArrayList<>();
    this.startDate = new Date();
    this.status = ChallengeStatus.OPEN;
  }

  public ChallengeReport(User user, Challenge challenge) {
    this.user = user;
    this.progressList = new ArrayList<>();
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

  public List<ChallengeProgress> getProgressList() {
    return progressList;
  }

  public void setProgressList(List<ChallengeProgress> progressList) {
    this.progressList = progressList;
  }

  public void addProgress(ChallengeProgress progress) {
    this.progressList.add(progress);
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
    return "ChallengeReport {id=" + id + ", user=" + user + ", challenge=" + challenge + ", progressList="
        + progressList + ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + ", version="
        + version + "}";
  }

}
