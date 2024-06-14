package blossom.reports_service.model.Entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import blossom.reports_service.model.Enums.Visibility;
import jakarta.persistence.*;

@Entity
@Table(name = "challenge_progress")
public class ChallengeProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_fk", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "challenge_fk")
    @OnDelete(action = OnDeleteAction.CASCADE) // Because I am not using a actual bidirectioanl relation (prevents
                                               // circular references) but still want to be able to delete
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "challenge_report_fk")
    private ChallengeReport challengeReport;

    @Column(name = "current_progress")
    private Double currentProgress;

    @Enumerated(EnumType.STRING)
    @Column(name = "progress_visibility")
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

    @Override
    public String toString() {
        return "ChallengeProgress [user=" + user + ", challenge=" + challenge + ", currentProgress=" + currentProgress
                + ", progressVisibility=" + progressVisibility + "]";
    }
}
