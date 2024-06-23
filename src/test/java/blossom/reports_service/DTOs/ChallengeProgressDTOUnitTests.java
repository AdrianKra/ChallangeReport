package blossom.reports_service.DTOs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import blossom.reports_service.inbound.DTOs.ChallengeProgressDTO;
import blossom.reports_service.model.Entities.Challenge;
import blossom.reports_service.model.Entities.ChallengeProgress;
import blossom.reports_service.model.Entities.ChallengeReport;
import blossom.reports_service.model.Entities.User;
import blossom.reports_service.model.Enums.Visibility;

public class ChallengeProgressDTOUnitTests {

  private ChallengeProgressDTO challengeProgressDTO;

  @Mock
  private User user;

  @Mock
  private Challenge challenge;

  @Mock
  private ChallengeReport challengeReport;

  private final Long id = 1L;
  private final Double currentProgress = 50.0;
  private final Visibility progressVisibility = Visibility.PUBLIC;
  private final int version = 0;

  @BeforeEach
  public void setUp() {
    user = mock(User.class);
    challenge = mock(Challenge.class);
    challengeReport = mock(ChallengeReport.class);
    challengeProgressDTO = new ChallengeProgressDTO(user, challenge, challengeReport, currentProgress,
        progressVisibility);
    challengeProgressDTO.setId(id);
  }

  @Test
  public void testDefaultConstructor() {
    ChallengeProgressDTO dto = new ChallengeProgressDTO();
    assertNotNull(dto);
  }

  @Test
  public void testParameterizedConstructor() {
    assertEquals(id, challengeProgressDTO.getId());
    assertEquals(user, challengeProgressDTO.getUser());
    assertEquals(challenge, challengeProgressDTO.getChallenge());
    assertEquals(challengeReport, challengeProgressDTO.getChallengeReport());
    assertEquals(currentProgress, challengeProgressDTO.getCurrentProgress());
    assertEquals(progressVisibility, challengeProgressDTO.getProgressVisibility());
    assertEquals(version, challengeProgressDTO.getVersion());
  }

  @Test
  public void testSettersAndGetters() {
    User newUser = new User();
    Challenge newChallenge = new Challenge();
    ChallengeReport newChallengeReport = new ChallengeReport();

    challengeProgressDTO.setId(2L);
    challengeProgressDTO.setUser(newUser);
    challengeProgressDTO.setChallenge(newChallenge);
    challengeProgressDTO.setChallengeReport(newChallengeReport);
    challengeProgressDTO.setCurrentProgress(75.0);
    challengeProgressDTO.setProgressVisibility(Visibility.PRIVATE);
    challengeProgressDTO.setVersion(1);

    assertEquals(2L, challengeProgressDTO.getId());
    assertEquals(newUser, challengeProgressDTO.getUser());
    assertEquals(newChallenge, challengeProgressDTO.getChallenge());
    assertEquals(newChallengeReport, challengeProgressDTO.getChallengeReport());
    assertEquals(75.0, challengeProgressDTO.getCurrentProgress());
    assertEquals(Visibility.PRIVATE, challengeProgressDTO.getProgressVisibility());
    assertEquals(1, challengeProgressDTO.getVersion());
  }

  @Test
  public void testConstructorFromChallengeProgress() {
    ChallengeProgress challengeProgress = new ChallengeProgress();
    challengeProgress.setId(id);
    challengeProgress.setUser(user);
    challengeProgress.setChallenge(challenge);
    challengeProgress.setChallengeReport(challengeReport);
    challengeProgress.setCurrentProgress(50.0);
    challengeProgress.setProgressVisibility(Visibility.PUBLIC);
    challengeProgress.setVersion(version);

    ChallengeProgressDTO dto = new ChallengeProgressDTO(challengeProgress);

    assertEquals(id, dto.getId());
    assertEquals(user, dto.getUser());
    assertEquals(challenge, dto.getChallenge());
    assertEquals(challengeReport, dto.getChallengeReport());
    assertEquals(50.0, dto.getCurrentProgress());
    assertEquals(Visibility.PUBLIC, dto.getProgressVisibility());
    assertEquals(version, dto.getVersion());
  }

  @Test
  public void testToString() {
    String expected = "ChallengeProgressDTO [challenge=" + challenge + ", challengeReport=" + challengeReport
        + ", currentProgress="
        + currentProgress + ", id=" + id + ", progressVisibility=" + progressVisibility + ", user=" + user
        + ", version="
        + version + "]";
    assertEquals(expected, challengeProgressDTO.toString());
  }
}
