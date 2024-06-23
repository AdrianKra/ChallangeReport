package blossom.reports_service;

import blossom.reports_service.model.Entities.User;
import blossom.reports_service.model.Enums.Unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import blossom.reports_service.model.Entities.Challenge;
import blossom.reports_service.model.Entities.ChallengeProgress;
import blossom.reports_service.model.Entities.ChallengeSummary;
import blossom.reports_service.model.Repositories.ChallengeProgressRepository;
import blossom.reports_service.model.Repositories.ChallengeReportRepository;
import blossom.reports_service.model.Repositories.ChallengeRepository;
import blossom.reports_service.model.Repositories.ChallengeSummaryRepository;
import blossom.reports_service.model.Repositories.UserRepository;
import blossom.reports_service.model.Enums.Visibility;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
public class RepositoryTests {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ChallengeRepository challengeRepository;

  @Autowired
  private ChallengeProgressRepository challengeProgressRepository;

  @Autowired
  private ChallengeReportRepository challengeReportRepository;

  @Autowired
  private ChallengeSummaryRepository challengeSummaryRepository;

  @Test
  public void testUserInsertion() {
    List<User> users = userRepository.findAll();
    assertThat(users).hasSize(5);

    User user1 = userRepository.findByEmail("user1@example.org").orElseThrow();
    assertThat(user1.getEmail()).isEqualTo("user1@example.org");
    assertThat(user1.getName()).isEqualTo("User 1");
    assertThat(user1.getVersion()).isEqualTo(0);

    User user2 = userRepository.findById(2L).orElseThrow();
    assertThat(user2.getEmail()).isEqualTo("user2@example.org");
    assertThat(user2.getName()).isEqualTo("User 2");
    assertThat(user2.getVersion()).isEqualTo(0);

    User user3 = userRepository.findById(3L).orElseThrow();
    assertThat(user3.getEmail()).isEqualTo("user3@example.org", 0);
    assertThat(user3.getName()).isEqualTo("User 3", 0);
    assertThat(user3.getVersion()).isEqualTo(0);

    User user4 = userRepository.findById(4L).orElseThrow();
    assertThat(user4.getEmail()).isEqualTo("user4@example.org", 0);
    assertThat(user4.getName()).isEqualTo("User 4", 0);
    assertThat(user4.getVersion()).isEqualTo(0);

    User user5 = userRepository.findById(5L).orElseThrow();
    assertThat(user5.getEmail()).isEqualTo("paul@gmail.com", 0);
    assertThat(user5.getName()).isEqualTo("Paul", 0);
    assertThat(user5.getVersion()).isEqualTo(0);

  }

  @Test
  public void testChallengeInsertion() {
    List<Challenge> challenges = challengeRepository.findAll();
    assertThat(challenges).hasSize(4);

    Challenge challenge1 = challengeRepository.findById(1L).orElseThrow();
    assertThat(challenge1.getTitle()).isEqualTo("Challenge 1");
    assertThat(challenge1.getDescription()).isEqualTo("Description 1");
    assertThat(challenge1.getUnit()).isEqualTo(Unit.SECONDS);
    assertThat(challenge1.getTargetProgress()).isEqualTo(100.0);
    assertThat(challenge1.getDeadline()).isEqualTo("2021-12-31");
    assertThat(challenge1.getScoreReward()).isEqualTo(10);
    assertThat(challenge1.getScorePenalty()).isEqualTo(5);
    assertThat(challenge1.getUser().getId()).isEqualTo(1L);
    assertThat(challenge1.getChallengeVisibility()).isEqualTo(Visibility.PUBLIC);
    assertThat(challenge1.getVersion()).isEqualTo(0);
  }

  @Test
  public void testChallengeProgressInsertion() {
    List<ChallengeProgress> progresses = challengeProgressRepository.findAll();
    assertThat(progresses).hasSize(3);

    ChallengeProgress progress1 = challengeProgressRepository.findById(1L).orElseThrow();
    assertThat(progress1.getCurrentProgress()).isEqualTo(50.0);
    assertThat(progress1.getProgressVisibility()).isEqualTo(Visibility.PUBLIC);
    assertThat(progress1.getVersion()).isEqualTo(0);
  }

  // @Test
  // public void testChallengeReportInsertion() {
  // List<ChallengeReport> reports = challengeReportRepository.findAll();
  // assertThat(reports).hasSize(3);

  // ChallengeReport report1 =
  // challengeReportRepository.findById(1L).orElseThrow();
  // assertThat(report1.getStatus()).isEqualTo("OPEN");
  // assertThat(report1.getStartDate()).isEqualTo("2021-12-01");
  // assertThat(report1.getEndDate()).isNull();
  // assertThat(report1.getUser().getId()).isEqualTo(1L);
  // assertThat(report1.getVersion()).isEqualTo(0);
  // }

  @Test
  public void testChallengeSummaryInsertion() {
    List<ChallengeSummary> summaries = challengeSummaryRepository.findAll();
    assertThat(summaries).hasSize(3);

    ChallengeSummary summary1 = challengeSummaryRepository.findById(1L).orElseThrow();
    assertThat(summary1.getChallengeCount()).isEqualTo(1);
    assertThat(summary1.getConsecutiveDays()).isEqualTo(0);
    assertThat(summary1.getDoneCount()).isEqualTo(0);
    assertThat(summary1.getLongestStreak()).isEqualTo(0);
    assertThat(summary1.getOverdueCount()).isEqualTo(0);
    assertThat(summary1.getPendingCount()).isEqualTo(1);
    assertThat(summary1.getVersion()).isEqualTo(0);
    assertThat(summary1.getUser().getId()).isEqualTo(1L);
    assertThat(summary1.getLastActive()).isNotNull();
  }
}
