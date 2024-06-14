package blossom.reports_service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Date;

import blossom.reports_service.model.Entities.Challenge;
import blossom.reports_service.model.Entities.ChallengeProgress;
import blossom.reports_service.model.Entities.ChallengeReport;
import blossom.reports_service.model.Entities.User;
import blossom.reports_service.model.Enums.ChallengeStatus;

@ExtendWith(MockitoExtension.class)
public class UnitTests {

  @Mock
  private Challenge challenge;

  @Mock
  private User user;

  @Mock
  private ChallengeProgress challengeProgress;

  @Test
  public void testChallengeReport() {

    Date date = new Date();

    given(challenge.getTitle()).willReturn("Challenge 1");
    given(challenge.getDescription()).willReturn("Challenge Description");
    given(challenge.getDeadline()).willReturn(date);

    ChallengeReport challengeReport = new ChallengeReport(user, challenge);

    assertNotNull(challengeReport.getChallenge());
    assertNotNull(challengeReport.getStartDate());
    assertNull(challengeReport.getEndDate());
    assertEquals(ChallengeStatus.OPEN, challengeReport.getStatus());

    assertEquals("Challenge 1", challenge.getTitle());
    assertEquals("Challenge Description", challenge.getDescription());
    assertEquals(date, challenge.getDeadline());
  }
}
