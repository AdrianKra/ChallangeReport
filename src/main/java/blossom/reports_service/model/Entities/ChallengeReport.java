package blossom.reports_service.model.Entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import blossom.reports_service.model.Enums.ChallengeStatus;
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
  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_FK")
  private User user;

  @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "challenge_progress_FK")
  private List<ChallengeProgress> challengeProgresses;

  private Date startDate;
  private Date endDate;

  @Enumerated(EnumType.STRING)
  private ChallengeStatus status;

  @Version
  private int version;

  public ChallengeReport() {
    this.status = ChallengeStatus.OPEN;
  }

  public ChallengeReport(User user, Challenge challenge) {
    this.user = user;
    this.challengeProgresses = new ArrayList<>();
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

  public List<ChallengeProgress> getChallengeProgresses() {
    return challengeProgresses;
  }

  public void setChallengeProgresses(List<ChallengeProgress> challengeProgresses) {
    this.challengeProgresses = challengeProgresses;
  }

  public void addChallengeProgress(ChallengeProgress challengeProgress) {
    this.challengeProgresses.add(challengeProgress);
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

}
