package blossom.reports_service.model;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.*;

@Entity
public class ActivityReport {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "activity_id", referencedColumnName = "id")
  private Activity activity;

  private String name;
  private Date creationDate;
  private String createdBy;
  private String description;
  private ActivityStatus status;

  public ActivityReport() {
    this.status = ActivityStatus.OPEN;
  }

  @Autowired
  public ActivityReport(Activity activity, String name, Date creationDate, String createdBy, String description) {
    this.activity = activity;
    this.name = name;
    this.creationDate = creationDate;
    this.createdBy = createdBy;
    this.description = description;
    this.status = ActivityStatus.OPEN;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Activity getActivity() {
    return activity;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
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

  @Override
  public String toString() {
    return "ActivityReport{" +
        "id=" + id +
        ", activity=" + activity +
        ", name='" + name + '\'' +
        ", creationDate=" + creationDate +
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

    ActivityReport that = (ActivityReport) o;

    if (id != null ? !id.equals(that.id) : that.id != null)
      return false;
    if (activity != null ? !activity.equals(that.activity) : that.activity != null)
      return false;
    if (name != null ? !name.equals(that.name) : that.name != null)
      return false;
    if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null)
      return false;
    if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null)
      return false;
    if (description != null ? !description.equals(that.description) : that.description != null)
      return false;
    return status != null ? status.equals(that.status) : that.status == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (activity != null ? activity.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
    result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (status != null ? status.hashCode() : 0);
    return result;
  }

}
