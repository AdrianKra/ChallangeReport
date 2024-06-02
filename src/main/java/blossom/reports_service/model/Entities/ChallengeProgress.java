
// import blossom.reports_service.model.Entities.Challenge;

// import org.hibernate.annotations.OnDelete;
// import org.hibernate.annotations.OnDeleteAction;
// import jakarta.persistence.*;

// @Entity
// @Table(name = "challenge_progress")
// public class ChallengeProgress {

// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;

// @ManyToOne
// @JoinColumn(name = "user_id", referencedColumnName = "id")
// private User user;

// @ManyToOne
// @JoinColumn(name = "challenge_id")
// @OnDelete(action = OnDeleteAction.CASCADE) // Because I am not using a actual
// bidirectioanl relation (prevents
// // circular references) but still want to be able to delete
// private Challenge challenge;

// @Column(name = "current_progress")
// private Double currentProgress;

// @Enumerated(EnumType.STRING)
// @Column(name = "progress_visibility")
// private Visibility progressVisibility;

// @Version
// private int version;

// /*
// * Default constructor; is required for entities by JPA
// */
// public ChallengeProgress() {
// }

// public ChallengeProgress(User user, Challenge challenge, Double
// currentProgress, Visibility progressVisibility) {
// this.user = user;
// this.challenge = challenge;
// this.currentProgress = currentProgress;
// this.progressVisibility = progressVisibility;
// }

// @ExcludeFromJacocoGeneratedReport
// public Long getId() {
// return id;
// }

// @ExcludeFromJacocoGeneratedReport
// public void setId(Long id) {
// this.id = id;
// }

// @ExcludeFromJacocoGeneratedReport
// public User getUser() {
// return user;
// }

// @ExcludeFromJacocoGeneratedReport
// public void setUser(User user) {
// this.user = user;
// }

// @ExcludeFromJacocoGeneratedReport
// public Challenge getChallenge() {
// return challenge;
// }

// @ExcludeFromJacocoGeneratedReport
// public void setChallenge(Challenge challenge) {
// this.challenge = challenge;
// }

// @ExcludeFromJacocoGeneratedReport
// public Double getCurrentProgress() {
// return currentProgress;
// }

// @ExcludeFromJacocoGeneratedReport
// public void setCurrentProgress(Double currentProgress) {
// this.currentProgress = currentProgress;
// }

// @ExcludeFromJacocoGeneratedReport
// public Visibility getProgressVisibility() {
// return progressVisibility;
// }

// @ExcludeFromJacocoGeneratedReport
// public void setProgressVisibility(Visibility progressVisibility) {
// this.progressVisibility = progressVisibility;
// }

// @ExcludeFromJacocoGeneratedReport
// @Override
// public String toString() {
// return "ChallengeProgress [user=" + user + ", challenge=" + challenge + ",
// currentProgress=" + currentProgress
// + ", progressVisibility=" + progressVisibility + "]";
// }
// }
