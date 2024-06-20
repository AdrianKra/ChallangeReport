package blossom.reports_service.model.Services;

import feign.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
public class RetryableFriendshipApiClient {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final FriendshipApiClient friendshipApiClient;

    @Autowired
    public RetryableFriendshipApiClient(FriendshipApiClient friendshipApiClient) {
        this.friendshipApiClient = friendshipApiClient;
    }

    @Retryable(retryFor = RetryableException.class, maxAttempts = 3, // first attempt and 2 retries
            backoff = @Backoff(delay = 100, maxDelay = 500))
    public Boolean isFriendWith(String userEmail1, String userEmail2) {
        LOGGER.info("Checking friendship between {} and {}", userEmail1, userEmail2);
        return friendshipApiClient.isFriendWith(userEmail1, userEmail2);
    }

    @Recover
    public Boolean fallbackFriendshipStatus(RetryableException e) {
        LOGGER.error("Friendship API is down. Returning false for every friendship check.");
        return false;
    }
}
