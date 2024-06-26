package blossom.reports_service.model.Entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import blossom.reports_service.model.Enums.Visibility;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "challenge_progress")
public class ChallengeProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_fk", referencedColumnName = "id")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "challenge_fk")
    @OnDelete(action = OnDeleteAction.CASCADE) // Because I am not using a actual bidirectioanl relation (prevents
                                               // circular references) but still want to be able to delete
    @NotNull
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "challenge_report_fk")
    private ChallengeReport challengeReport;

    @Column(name = "current_progress")
    @NotNull
    private Double currentProgress;

    @Enumerated(EnumType.STRING)
    @Column(name = "progress_visibility")
    @NotNull
    private Visibility progressVisibility;

    @Version
    private int version;

    public ChallengeProgress() {
    }

    public ChallengeProgress(User user, Challenge challenge, Double currentProgress, Visibility progressVisibility) {
        this.user = user;
        this.challenge = challenge;
        this.currentProgress = currentProgress;
        this.progressVisibility = progressVisibility;
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

    public ChallengeReport getChallengeReport() {
        return challengeReport;
    }

    public void setChallengeReport(ChallengeReport challengeReport) {
        this.challengeReport = challengeReport;
    }

    public Double getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(Double currentProgress) {
        this.currentProgress = currentProgress;
    }

    public Visibility getProgressVisibility() {
        return progressVisibility;
    }

    public void setProgressVisibility(Visibility progressVisibility) {
        this.progressVisibility = progressVisibility;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ChallengeProgress {id=" + id + ", challenge=" + challenge + ", user=" + user + ", challengeReport="
                + challengeReport + ", currentProgress=" + currentProgress + ", progressVisibility="
                + progressVisibility
                + ", version=" + version + "}";
    }
}
