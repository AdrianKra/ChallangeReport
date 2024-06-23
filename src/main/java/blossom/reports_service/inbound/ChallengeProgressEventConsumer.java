package blossom.reports_service.inbound;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import blossom.reports_service.model.ChallengeProgressUpdateEvent;
import blossom.reports_service.model.Services.ReportsService;
import jakarta.annotation.PostConstruct;

@Component
public class ChallengeProgressEventConsumer implements Consumer<ChallengeProgressUpdateEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChallengeProgressEventConsumer.class);

  private ReportsService reportsService;

  @Autowired
  public ChallengeProgressEventConsumer(ReportsService reportsService) {
    this.reportsService = reportsService;
  }

  @Override
  public void accept(ChallengeProgressUpdateEvent t) {
    LOGGER.info("Received challenge progress update event: {}", t);
    reportsService.updateChallengeProgress(
        t.challengeId(),
        t.challengeProgressId(),
        t.userEmail(),
        t.currentProgress(),
        t.timestamp());

  }

  @PostConstruct
  public void init() {
    LOGGER.info("Progress Update EventConsumer initialized");
  }
}
