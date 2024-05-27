package blossom.reports_service.inbound;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import blossom.reports_service.model.ChallengeProgressUpdateEvent;
import blossom.reports_service.model.ReportsService;

@Component
public class EventConsumer implements Consumer<ChallengeProgressUpdateEvent> {

  private ReportsService reportsService;

  @Autowired
  public EventConsumer(ReportsService reportsService) {
    this.reportsService = reportsService;
  }

  @Override
  public void accept(ChallengeProgressUpdateEvent t) {
    reportsService.updateChallengeProgress(
        t.challengeId(),
        t.userEmail(),
        t.currentProgress(),
        t.timestamp());

  }
}
