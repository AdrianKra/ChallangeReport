package blossom.reports_service.model;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.*;

@Entity
public class ChallengeReport {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "challenge_FK")
  private Challenge challenge;

  // fk of user
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_FK")
  private User user;

  private String name;
  private Date startDate;
  private Date endDate;
  private String createdBy;
  private String description;

  @Enumerated(EnumType.STRING)
  private ChallengeStatus status;

  public ChallengeReport() {
    this.status = ChallengeStatus.OPEN;
  }

  @Autowired
  public ChallengeReport(Challenge challenge, User user, String name, Date startDate, String createdBy,
      String description) {

    this.user = user;
    this.challenge = challenge;
    this.name = name;
    this.startDate = startDate;
    this.endDate = null;
    this.createdBy = createdBy;
    this.description = description;
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

  public ChallengeStatus getStatus() {
    return status;
  }

  public void setStatus(ChallengeStatus status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "ChallengeReport{" +
        "id=" + id +
        ", challenge=" + challenge +
        ", name='" + name + '\'' +
        ", creationDate=" + startDate +
        ", endDate=" + endDate +
        ", createdBy='" + createdBy + '\'' +
        ", description='" + description + '\'' +
        ", status=" + status +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    ChallengeReport that = (ChallengeReport) o;

    if (id != null ? !id.equals(that.id) : that.id != null)
      return false;
    if (challenge != null ? !challenge.equals(that.challenge) : that.challenge != null)
      return false;
    if (name != null ? !name.equals(that.name) : that.name != null)
      return false;
    if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null)
      return false;
    if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) {
      return false;
    }
    if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null)
      return false;
    if (description != null ? !description.equals(that.description) : that.description != null)
      return false;
    return status != null ? status.equals(that.status) : that.status == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (challenge != null ? challenge.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
    result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
    result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (status != null ? status.hashCode() : 0);
    return result;
  }

}
