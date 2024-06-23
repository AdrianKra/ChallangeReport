package blossom.reports_service.inbound;

import java.util.Optional;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import blossom.reports_service.model.UserRegisteredEvent;
import blossom.reports_service.model.Entities.User;
import blossom.reports_service.model.Exceptions.AlreadyExistsException;
import blossom.reports_service.model.Repositories.UserRepository;
import blossom.reports_service.model.Services.ReportsService;
import jakarta.annotation.PostConstruct;

@Component
public class UserRegisteredEventConsumer implements Consumer<UserRegisteredEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRegisteredEventConsumer.class);

  private ReportsService reportsService;

  private UserRepository userRepository;

  @Autowired
  public UserRegisteredEventConsumer(ReportsService reportsService, UserRepository userRepository) {
    this.reportsService = reportsService;
    this.userRepository = userRepository;
  }

  @Override
  public void accept(UserRegisteredEvent t) {
    LOGGER.info("Received user registered event: {}", t);
    // get user by email
    Optional<User> userOpt = userRepository.findByEmail(t.email());
    if (userOpt.isPresent()) {
      throw new AlreadyExistsException("User with email " + t.email() + " already exists");
    }
    User user = new User(t.name(), t.email());
    userRepository.save(user);
    LOGGER.info("User saved: {}", user);

    reportsService.createChallengeSummary(t.email());
  }

  @PostConstruct
  public void init() {
    LOGGER.info("User Registered EventConsumer initialized");
  }
}
