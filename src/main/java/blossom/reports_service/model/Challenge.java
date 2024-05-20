package blossom.reports_service.model;

import java.util.*;
import jakarta.persistence.*;

@Entity
@Table(name = "challenges", catalog = "", schema = "")
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    private double targetProgress;

    @Temporal(TemporalType.DATE)
    private Date deadline;

    private Integer scoreReward;

    private Integer scorePenalty;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Visibility challengeVisibility;

    @Version
    private int version;

    /**
     * Default constructor; is required for entities by JPA
     */
    public Challenge() {
    }

    public Challenge(String title, String description, Unit unit,
            double targetProgress, Date deadline, Integer scoreReward, Integer scorePenalty, User user,
            Visibility challengeVisibility) {
        this.title = title;
        this.description = description;
        this.unit = unit;
        this.targetProgress = targetProgress;
        this.deadline = deadline;
        this.scoreReward = scoreReward;
        this.scorePenalty = scorePenalty;
        this.user = user;
        this.challengeVisibility = challengeVisibility;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public double getTargetProgress() {
        return targetProgress;
    }

    public void setTargetProgress(double targetProgress) {
        this.targetProgress = targetProgress;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getScoreReward() {
        return scoreReward;
    }

    public void setScoreReward(Integer scoreReward) {
        this.scoreReward = scoreReward;
    }

    public Integer getScorePenalty() {
        return scorePenalty;
    }

    public void setScorePenalty(Integer scorePenalty) {
        this.scorePenalty = scorePenalty;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Visibility getChallengeVisibility() {
        return challengeVisibility;
    }

    public void setChallengeVisibility(Visibility challengeVisibility) {
        this.challengeVisibility = challengeVisibility;
    }

    @Override
    public String toString() {
        return "Challenge [id=" + id + ", title=" + title + ", description=" + description + ", unit=" + unit
                + ", targetProgress=" + targetProgress + ", deadline=" + deadline + ", scoreReward=" + scoreReward
                + ", scorePenalty=" + scorePenalty + ", user=" + user + ", challengeVisibility="
                + challengeVisibility + "]";
    }

}