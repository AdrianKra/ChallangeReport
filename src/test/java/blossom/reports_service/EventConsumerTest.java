// package blossom.reports_service;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.cloud.stream.binder.test.InputDestination;
// import org.springframework.context.annotation.Import;
// import
// org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
// import org.springframework.messaging.support.MessageBuilder;

// import blossom.reports_service.model.ChallengeProgressUpdateEvent;
// import blossom.reports_service.model.Services.ReportsService;

// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;

// @SpringBootTest
// @Import(TestChannelBinderConfiguration.class)
// public class EventConsumerTest {

// @Autowired
// private InputDestination inputDestination;

// @MockBean
// private ReportsService reportsService;

// @Test
// public void testEventConsumer() {
// this.inputDestination
// .send(MessageBuilder.withPayload(new ChallengeProgressUpdateEvent(1L,
// "example@org.de", 1.0, "..."))
// .build());
// verify(this.reportsService, times(1)).updateChallengeProgress(1L,
// "example@org.de", 1.0, "...");
// }
// }
