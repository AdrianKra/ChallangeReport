package blossom.reports_service.DTOs;

import blossom.reports_service.inbound.DTOs.ChallengeProgressDTO;
import blossom.reports_service.model.Entities.Challenge;
import blossom.reports_service.model.Entities.ChallengeProgress;
import blossom.reports_service.model.Entities.ChallengeReport;
import blossom.reports_service.model.Entities.User;
import blossom.reports_service.model.Enums.Visibility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ChallengeProgressDTOUnitTests {

  private ChallengeProgressDTO challengeProgressDTO;

  @Mock
  private User user;

  @Mock
  private Challenge challenge;

  @Mock
  private ChallengeReport challengeReport;

  @BeforeEach
  public void setUp() {
    user = mock(User.class);
    challenge = mock(Challenge.class);
    challengeReport = mock(ChallengeReport.class);
    challengeProgressDTO = new ChallengeProgressDTO(user, challenge, challengeReport, 50.0, Visibility.PUBLIC);
  }

  @Test
  public void testDefaultConstructor() {
    ChallengeProgressDTO dto = new ChallengeProgressDTO();
    assertNotNull(dto);
  }

  @Test
  public void testParameterizedConstructor() {
    assertEquals(user, challengeProgressDTO.getUser());
    assertEquals(challenge, challengeProgressDTO.getChallenge());
    assertEquals(challengeReport, challengeProgressDTO.getChallengeReport());
    assertEquals(50.0, challengeProgressDTO.getCurrentProgress());
    assertEquals(Visibility.PUBLIC, challengeProgressDTO.getProgressVisibility());
  }

  @Test
  public void testSettersAndGetters() {
    User newUser = new User();
    Challenge newChallenge = new Challenge();
    ChallengeReport newChallengeReport = new ChallengeReport();

    challengeProgressDTO.setUser(newUser);
    challengeProgressDTO.setChallenge(newChallenge);
    challengeProgressDTO.setChallengeReport(newChallengeReport);
    challengeProgressDTO.setCurrentProgress(75.0);
    challengeProgressDTO.setProgressVisibility(Visibility.PRIVATE);

    assertEquals(newUser, challengeProgressDTO.getUser());
    assertEquals(newChallenge, challengeProgressDTO.getChallenge());
    assertEquals(newChallengeReport, challengeProgressDTO.getChallengeReport());
    assertEquals(75.0, challengeProgressDTO.getCurrentProgress());
    assertEquals(Visibility.PRIVATE, challengeProgressDTO.getProgressVisibility());
  }

  @Test
  public void testConstructorFromChallengeProgress() {
    ChallengeProgress challengeProgress = new ChallengeProgress();
    challengeProgress.setUser(user);
    challengeProgress.setChallenge(challenge);
    challengeProgress.setChallengeReport(challengeReport);
    challengeProgress.setCurrentProgress(50.0);
    challengeProgress.setProgressVisibility(Visibility.PUBLIC);

    ChallengeProgressDTO dto = new ChallengeProgressDTO(challengeProgress);

    assertEquals(user, dto.getUser());
    assertEquals(challenge, dto.getChallenge());
    assertEquals(challengeReport, dto.getChallengeReport());
    assertEquals(50.0, dto.getCurrentProgress());
    assertEquals(Visibility.PUBLIC, dto.getProgressVisibility());
  }
}
