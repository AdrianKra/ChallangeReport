package blossom.reports_service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import blossom.reports_service.model.Entities.Challenge;
import blossom.reports_service.model.Entities.ChallengeProgress;
import blossom.reports_service.model.Entities.ChallengeReport;
import blossom.reports_service.model.Entities.ChallengeSummary;
import blossom.reports_service.model.Entities.User;
import blossom.reports_service.model.Enums.ChallengeStatus;
import blossom.reports_service.model.Enums.Unit;
import blossom.reports_service.model.Enums.Visibility;

@ExtendWith(MockitoExtension.class)
public class UnitTests {

  @Mock
  private Challenge challenge;

  @Mock
  private User user;

  @Mock
  private ChallengeProgress challengeProgress;

  @Mock
  private ChallengeReport challengeReport;

  @Mock
  private ChallengeSummary challengeSummary;

  @Test
  public void testChallengeReport() {
    ChallengeReport challengeReport = new ChallengeReport();
    challengeReport.setId(1L);
    challengeReport.setChallenge(challenge);
    challengeReport.setUser(user);
    challengeReport.addProgress(challengeProgress);
    challengeReport.setStartDate(new Date());
    challengeReport.setEndDate(null);
    challengeReport.setStatus(ChallengeStatus.OPEN);
    challengeReport.setVersion(0);

    assertEquals(1L, challengeReport.getId());
    assertEquals(challenge, challengeReport.getChallenge());
    assertEquals(user, challengeReport.getUser());
    assertEquals(challengeProgress, challengeReport.getProgressList().get(0));
    assertEquals(1, challengeReport.getProgressList().size());
    assertEquals(challengeProgress, challengeReport.getProgressList().get(0));
    assertEquals(0, challengeReport.getVersion());
    assertEquals(ChallengeStatus.OPEN, challengeReport.getStatus());

    // test toString
    assertEquals(
        "ChallengeReport {id=1, user=" + user + ", challenge=" + challenge + ", progressList="
            + challengeReport.getProgressList()
            + ", startDate=" + challengeReport.getStartDate() + ", endDate=" + challengeReport.getEndDate()
            + ", status=OPEN, version=0}",
        challengeReport.toString());
  }

  @Test
  public void testChallengeReportConstructor() {
    ChallengeReport challengeReport = new ChallengeReport(user, challenge);

    assertEquals(user, challengeReport.getUser());
    assertEquals(challenge, challengeReport.getChallenge());
    assertNotNull(challengeReport.getProgressList());
    assertNotNull(challengeReport.getStartDate());
    assertNull(challengeReport.getEndDate());
    assertEquals(ChallengeStatus.OPEN, challengeReport.getStatus());
  }

  @Test
  public void testChallengeSummary() {
    ChallengeSummary challengeSummary = new ChallengeSummary(user);
    challengeSummary.setId(1L);
    challengeSummary.setUser(user);
    challengeSummary.setLastActive(new Date());
    challengeSummary.setChallengeCount(0);
    challengeSummary.setDoneCount(0);
    challengeSummary.setPendingCount(0);
    challengeSummary.setOverdueCount(0);
    challengeSummary.setConsecutiveDays(0);
    challengeSummary.setLongestStreak(0);
    challengeSummary.setVersion(0);

    assertEquals(1L, challengeSummary.getId());
    assertEquals(user, challengeSummary.getUser());
    assertEquals(0, challengeSummary.getChallengeCount());
    assertEquals(0, challengeSummary.getDoneCount());
    assertEquals(0, challengeSummary.getPendingCount());
    assertEquals(0, challengeSummary.getOverdueCount());
    assertEquals(0, challengeSummary.getConsecutiveDays());
    assertEquals(0, challengeSummary.getLongestStreak());
    assertEquals(0, challengeSummary.getVersion());

    challengeSummary.incrementDoneCount();
    challengeSummary.incrementOverdueCount();
    challengeSummary.incrementConsecutiveDays();

    assertEquals(1, challengeSummary.getDoneCount());
    assertEquals(1, challengeSummary.getOverdueCount());
    assertEquals(1, challengeSummary.getConsecutiveDays());

    challengeSummary.decrementChallengeCount();
    challengeSummary.decrementDoneCount();
    challengeSummary.decrementPendingCount();
    challengeSummary.decrementOverdueCount();
    challengeSummary.resetConsecutiveDays();
    challengeSummary.updateLongestStreak();

    Date updatedDate = new Date(challengeSummary.getLastActive().getTime() + 1000);
    challengeSummary.updateLastActive(updatedDate);

    assertEquals(-1, challengeSummary.getChallengeCount());
    assertEquals(0, challengeSummary.getDoneCount());
    assertEquals(-1, challengeSummary.getPendingCount());
    assertEquals(0, challengeSummary.getOverdueCount());
    assertEquals(0, challengeSummary.getConsecutiveDays());
    assertEquals(0, challengeSummary.getLongestStreak());
    assertEquals(updatedDate, challengeSummary.getLastActive());

    // test toString
    assertEquals(
        "ChallengeSummary {id=1, user=" + user + ", lastActive=" + challengeSummary.getLastActive()
            + ", challengeCount=-1, doneCount=0, pendingCount=-1, overdueCount=0, consecutiveDays=0, longestStreak=0, version=0}",
        challengeSummary.toString());
  }

  @Test
  public void testChallengeProgressConstructor() {
    ChallengeProgress challengeProgress = new ChallengeProgress(user, challenge, 0.0, Visibility.PRIVATE);

    assertEquals(user, challengeProgress.getUser());
    assertEquals(challenge, challengeProgress.getChallenge());
    assertEquals(0.0, challengeProgress.getCurrentProgress());
    assertEquals(Visibility.PRIVATE, challengeProgress.getProgressVisibility());
  }

  @Test
  public void testChallengeProgress() {
    ChallengeProgress challengeProgress = new ChallengeProgress();
    challengeProgress.setId(1L);
    challengeProgress.setChallenge(challenge);
    challengeProgress.setUser(user);
    challengeProgress.setChallengeReport(challengeReport);
    challengeProgress.setCurrentProgress(0.0);
    challengeProgress.setProgressVisibility(Visibility.PRIVATE);
    challengeProgress.setVersion(0);

    assertEquals(1L, challengeProgress.getId());
    assertEquals(challenge, challengeProgress.getChallenge());
    assertEquals(user, challengeProgress.getUser());
    assertEquals(challengeReport, challengeProgress.getChallengeReport());
    assertEquals(0.0, challengeProgress.getCurrentProgress());
    assertEquals(Visibility.PRIVATE, challengeProgress.getProgressVisibility());
    assertEquals(0, challengeProgress.getVersion());

    // test toString
    assertEquals(
        "ChallengeProgress {id=1, challenge=" + challenge + ", user=" + user + ", challengeReport=" + challengeReport
            + ", currentProgress=0.0, progressVisibility=PRIVATE, version=0}",
        challengeProgress.toString());
  }

  @Test
  public void testChallengeConstructor() {
    Challenge challenge = new Challenge("Challenge", "Description", Unit.HOURS, 0.0, new Date(), 0, 0, user,
        Visibility.PRIVATE);

    assertEquals("Challenge", challenge.getTitle());
    assertEquals("Description", challenge.getDescription());
    assertEquals(Unit.HOURS, challenge.getUnit());
    assertEquals(0.0, challenge.getTargetProgress());
    assertEquals(0, challenge.getScoreReward());
    assertEquals(0, challenge.getScorePenalty());
    assertEquals(user, challenge.getUser());
    assertEquals(Visibility.PRIVATE, challenge.getChallengeVisibility());
    assertEquals(0, challenge.getVersion());
  }

  @Test
  public void testChallenge() {
    Challenge challenge = new Challenge();
    challenge.setId(1L);
    challenge.setTitle("Challenge");
    challenge.setDescription("Description");
    challenge.setUnit(Unit.HOURS);
    challenge.setTargetProgress(0.0);
    challenge.setDeadline(new Date());
    challenge.setScoreReward(0);
    challenge.setScorePenalty(0);
    challenge.setUser(user);
    challenge.setChallengeVisibility(Visibility.PRIVATE);
    challenge.setVersion(0);

    assertEquals(1L, challenge.getId());
    assertEquals("Challenge", challenge.getTitle());
    assertEquals("Description", challenge.getDescription());
    assertEquals(Unit.HOURS, challenge.getUnit());
    assertEquals(0.0, challenge.getTargetProgress());
    assertEquals(0, challenge.getScoreReward());
    assertEquals(0, challenge.getScorePenalty());
    assertEquals(user, challenge.getUser());
    assertEquals(Visibility.PRIVATE, challenge.getChallengeVisibility());
    assertEquals(0, challenge.getVersion());

    // test toString
    assertEquals(
        "Challenge {id=1, title=Challenge, description=Description, unit=HOURS, targetProgress=0.0, deadline="
            + challenge.getDeadline() + ", scoreReward=0, scorePenalty=0, user=" + user
            + ", challengeVisibility=PRIVATE, version=0}",
        challenge.toString());
  }

  @Test
  public void testUserConstructor() {
    User user = new User("first@email.org");
    assertEquals("first@email.org", user.getEmail());
    assertEquals(0, user.getVersion());
  }

  @Test
  public void testUser() {
    User user = new User();
    user.setId(1L);
    user.setEmail("example@email.org");
    user.setVersion(0);

    assertEquals(1L, user.getId());
    assertEquals("example@email.org", user.getEmail());
    assertEquals(0, user.getVersion());
    // test toString
    assertEquals("User{id=1, email='example@email.org', version=0}",
        user.toString());
  }
}
