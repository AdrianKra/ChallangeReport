package blossom.reports_service.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Challenge Report already exists")
public class ChallengeReportAlreadyExistsException extends RuntimeException {
  public ChallengeReportAlreadyExistsException(String message) {
    super(message);
  }

}
