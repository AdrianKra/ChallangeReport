package blossom.reports_service.inbound.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller class for handling REST requests related to fallback
 * 
 * @RestController - Indicates that this class is a controller class for REST
 *                 requests
 * @RequestMapping - Annotation for mapping web requests onto methods in request
 */
@RestController
@RequestMapping("/fallback")
public class FallbackController {

  private final Logger LOGGER = LoggerFactory.getLogger(getClass());

  @Autowired
  public FallbackController() {
  }

  /**
   * Fallback method to handle service unavailable response.
   * 
   * @return ResponseEntity<String> - Service unavailable response
   *         (HTTP status code 503)
   */
  @GetMapping
  public ResponseEntity<String> fallback() {
    LOGGER.info("Fallback method called");
    return new ResponseEntity<>("Service unavailable", HttpStatus.SERVICE_UNAVAILABLE);
  }

}