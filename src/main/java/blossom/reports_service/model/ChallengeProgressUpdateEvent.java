package blossom.reports_service.model;

public record ChallengeProgressUpdateEvent(Long challengeId, String userEmail, Double currentProgress,
        String timestamp) {

}
