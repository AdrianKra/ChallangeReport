package blossom.reports_service.model;

import java.util.Date;

public record ChallengeProgressUpdateEvent(Long challengeId, Long challengeProgressId, String userEmail,
                Double currentProgress, Date timestamp) {
}
