package blossom.reports_service.model;

import java.util.Date;

/**
 * This class represents an event that is triggered when a challenge progress is
 * updated.
 */
public record ChallengeProgressUpdateEvent(Long challengeId, Long challengeProgressId, String userEmail,
    Double currentProgress, Date timestamp) {
}
