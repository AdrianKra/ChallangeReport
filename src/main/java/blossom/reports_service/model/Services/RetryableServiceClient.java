package blossom.reports_service.model.Services;

import org.springframework.stereotype.Component;

import blossom.reports_service.model.Quote;
import blossom.reports_service.model.Exceptions.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;

import feign.RetryableException;
import jakarta.persistence.OptimisticLockException;

import org.hibernate.StaleStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Retryable(include = { OptimisticLockException.class,
    StaleStateException.class }, maxAttempts = 3, backoff = @Backoff(delay = 100, maxDelay = 500))
public class RetryableServiceClient {

  private final Logger LOGGER = LoggerFactory.getLogger(RetryableServiceClient.class);

  private final QuotesServiceClient quotesClient;
  private final String apiKey;

  @Autowired
  public RetryableServiceClient(QuotesServiceClient quotesClient, @Value("${api.ninjas.key}") String apiKey) {
    this.quotesClient = quotesClient;
    this.apiKey = apiKey;
  }

  @Retryable(include = { RetryableException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000, maxDelay = 500))
  public Quote[] getQuotes(String category) {
    // return if list is not empty, otherwise throw exception
    LOGGER.info("Getting quotes for category: {}", category);
    Quote[] quotes = quotesClient.getQuotes(apiKey, category);
    if (quotes.length == 0) {
      throw new NotFoundException("No quotes found for the given category");
    }
    return quotes;
  }

  @Recover
  public Quote[] recover(NotFoundException e) {
    LOGGER.error("Failed to get quotes: {}. Use fallback! ", e.getMessage());
    return new Quote[] { new Quote("No quotes found for the given category", "Unknown") };
  }

}
