package blossom.reports_service.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Challenge Report already exists")
public class ChallengeNotFoundException extends RuntimeException {
  public ChallengeNotFoundException(String message) {
    super(message);
  }
}
