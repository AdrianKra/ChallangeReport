package blossom.reports_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.Date;

import static org.mockito.Mockito.times;

import blossom.reports_service.inbound.EventConsumer;
import blossom.reports_service.model.ChallengeProgressUpdateEvent;
import blossom.reports_service.model.Services.ReportsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
public class EventConsumerTest {

  @Mock
  private ReportsService reportsService;

  @Mock
  private Logger logger;

  @InjectMocks
  private EventConsumer eventConsumer;

  private final Date date = new Date();
  private ChallengeProgressUpdateEvent event;

  @BeforeEach
  public void setUp() {
    event = new ChallengeProgressUpdateEvent(1L, 1L, "user@example.com", 50.0, date);

    eventConsumer = new EventConsumer(reportsService);
  }

  @Test
  public void testAccept() {

    // Act
    eventConsumer.accept(event);

    // Assert
    verify(reportsService, times(1)).updateChallengeProgress(

        1L,
        1L,
        "user@example.com",
        50.0,
        date);
  }
}
