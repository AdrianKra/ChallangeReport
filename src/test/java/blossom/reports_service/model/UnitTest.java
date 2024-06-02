package blossom.reports_service.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import blossom.reports_service.model.Entities.Challenge;
import blossom.reports_service.model.Entities.ChallengeReport;
import blossom.reports_service.model.Entities.ChallengeSummary;
import blossom.reports_service.model.Entities.User;
import blossom.reports_service.model.Enums.ChallengeStatus;
import blossom.reports_service.model.Enums.Unit;
import blossom.reports_service.model.Enums.Visibility;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UnitTest {

  @Mock
  private Challenge challenge;

  @Mock
  private User user;

  java.util.Date date = new Date();

  @Test
  public void testChallengeReport() {

    given(challenge.getTitle()).willReturn("Challenge 1");
    given(challenge.getDescription()).willReturn("Challenge Description");
    given(challenge.getDeadline()).willReturn(date);

    ChallengeReport challengeReport = new ChallengeReport(challenge, user, date, "Challenge Report Description");

    assertNotNull(challengeReport.getChallenge());
    assertNotNull(challengeReport.getStartDate());
    assertNull(challengeReport.getEndDate());
    assertEquals("Challenge Report Description", challengeReport.getDescription());
    assertEquals(ChallengeStatus.OPEN, challengeReport.getStatus());

    assertEquals("Challenge 1", challenge.getTitle());
    assertEquals("Challenge Description", challenge.getDescription());
    assertEquals(date, challenge.getDeadline());
  }

  @Test
  public void testChallengeSummary() {
    ChallengeSummary challengeSummary = new ChallengeSummary(user);

    assertEquals(user, user);
    assertEquals(0, challengeSummary.getChallengeCount());
    assertEquals(0, challengeSummary.getDoneCount());
    assertEquals(0, challengeSummary.getPendingCount());
    assertEquals(0, challengeSummary.getOverdueCount());
    assertEquals(0, challengeSummary.getConsecutiveDays());
    assertEquals(0, challengeSummary.getLongestStreak());

  }

  @Test
  public void testChallenge() {
    Challenge challenge = new Challenge("Challenge 1", "Challenge Description", Unit.HOURS, 100, date, 10, 5, user,
        Visibility.PUBLIC);

    assertEquals("Challenge 1", challenge.getTitle());
    assertEquals("Challenge Description", challenge.getDescription());
    assertEquals(date, challenge.getDeadline());
  }
}
