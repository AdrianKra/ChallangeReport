package blossom.reports_service.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Activity Report already exists")
public class ActivityReportAlreadyExistsException extends RuntimeException {
  public ActivityReportAlreadyExistsException(String message) {
    super(message);
  }

}
