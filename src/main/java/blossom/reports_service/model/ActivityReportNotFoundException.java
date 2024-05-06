package blossom.reports_service.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ActivityReportNotFoundException extends RuntimeException {
  public ActivityReportNotFoundException(String message) {
    super(message);
  }
}
