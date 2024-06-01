// package blossom.reports_service.inbound;

// import java.util.function.Consumer;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// import blossom.reports_service.model.ChallengeProgressUpdateEvent;
// import blossom.reports_service.model.ReportsService;
// import jakarta.annotation.PostConstruct;
// import jakarta.annotation.PreDestroy;

// @Component
// public class EventConsumer implements Consumer<ChallengeProgressUpdateEvent>
// {

// private static final Logger LOGGER =
// LoggerFactory.getLogger(EventConsumer.class);

// private ReportsService reportsService;

// @Autowired
// public EventConsumer(ReportsService reportsService) {
// this.reportsService = reportsService;
// }

// @Override
// public void accept(ChallengeProgressUpdateEvent t) {
// reportsService.updateChallengeProgress(
// t.challengeId(),
// t.userEmail(),
// t.currentProgress(),
// t.timestamp());

// }

// @PostConstruct
// public void init() {
// LOGGER.info("EventConsumer initialized");
// }

// @PreDestroy
// public void destroy() {
// LOGGER.info("EventConsumer destroyed");
// }
// }
