package blossom.reports_service.DTOs;

import blossom.reports_service.inbound.DTOs.ChallengeDTO;
import blossom.reports_service.model.Entities.Challenge;
import blossom.reports_service.model.Entities.User;
import blossom.reports_service.model.Enums.Unit;
import blossom.reports_service.model.Enums.Visibility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ChallengeDTOUnitTests {

  @Mock
  private ChallengeDTO challengeDTO;

  @Mock
  private User user;

  private Date deadline;

  @BeforeEach
  public void setUp() {
    user = mock(User.class);
    deadline = mock(Date.class);
    challengeDTO = new ChallengeDTO(1L, "Challenge Title", "Challenge Description", Unit.MONTHS, 100.0, deadline,
        50, 10, user, Visibility.PUBLIC, 1);
  }

  @Test
  public void testDefaultConstructor() {
    ChallengeDTO dto = new ChallengeDTO();
    assertNotNull(dto);
  }

  @Test
  public void testParameterizedConstructor() {
    assertEquals(1L, challengeDTO.getId());
    assertEquals("Challenge Title", challengeDTO.getTitle());
    assertEquals("Challenge Description", challengeDTO.getDescription());
    assertEquals(Unit.MONTHS, challengeDTO.getUnit());
    assertEquals(100.0, challengeDTO.getTargetProgress());
    assertEquals(deadline, challengeDTO.getDeadline());
    assertEquals(50, challengeDTO.getScoreReward());
    assertEquals(10, challengeDTO.getScorePenalty());
    assertEquals(user, challengeDTO.getUser());
    assertEquals(Visibility.PUBLIC, challengeDTO.getChallengeVisibility());
    assertEquals(1, challengeDTO.getVersion());
  }

  @Test
  public void testSettersAndGetters() {
    User newUser = new User();
    Date newDeadline = new Date();

    challengeDTO.setId(2L);
    challengeDTO.setTitle("New Challenge Title");
    challengeDTO.setDescription("New Challenge Description");
    challengeDTO.setUnit(Unit.GRAMM);
    challengeDTO.setTargetProgress(200.0);
    challengeDTO.setDeadline(newDeadline);
    challengeDTO.setScoreReward(100);
    challengeDTO.setScorePenalty(20);
    challengeDTO.setUser(newUser);
    challengeDTO.setChallengeVisibility(Visibility.PRIVATE);
    challengeDTO.setVersion(2);

    assertEquals(2L, challengeDTO.getId());
    assertEquals("New Challenge Title", challengeDTO.getTitle());
    assertEquals("New Challenge Description", challengeDTO.getDescription());
    assertEquals(Unit.GRAMM, challengeDTO.getUnit());
    assertEquals(200.0, challengeDTO.getTargetProgress());
    assertEquals(newDeadline, challengeDTO.getDeadline());
    assertEquals(100, challengeDTO.getScoreReward());
    assertEquals(20, challengeDTO.getScorePenalty());
    assertEquals(newUser, challengeDTO.getUser());
    assertEquals(Visibility.PRIVATE, challengeDTO.getChallengeVisibility());
    assertEquals(2, challengeDTO.getVersion());
  }

  @Test
  public void testConstructorFromChallenge() {
    Challenge challenge = new Challenge();
    challenge.setId(1L);
    challenge.setTitle("Challenge Title");
    challenge.setDescription("Challenge Description");
    challenge.setUnit(Unit.MONTHS);
    challenge.setTargetProgress(100.0);
    challenge.setDeadline(deadline);
    challenge.setScoreReward(50);
    challenge.setScorePenalty(10);
    challenge.setUser(user);
    challenge.setChallengeVisibility(Visibility.PUBLIC);
    challenge.setVersion(1);

    ChallengeDTO dto = new ChallengeDTO(challenge);

    assertEquals(1L, dto.getId());
    assertEquals("Challenge Title", dto.getTitle());
    assertEquals("Challenge Description", dto.getDescription());
    assertEquals(Unit.MONTHS, dto.getUnit());
    assertEquals(100.0, dto.getTargetProgress());
    assertEquals(deadline, dto.getDeadline());
    assertEquals(50, dto.getScoreReward());
    assertEquals(10, dto.getScorePenalty());
    assertEquals(user, dto.getUser());
    assertEquals(Visibility.PUBLIC, dto.getChallengeVisibility());
    assertEquals(1, dto.getVersion());
  }
}
