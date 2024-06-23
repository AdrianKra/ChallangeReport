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

  private final Date date = new Date();

  private final Long id = 1L;
  private final String title = "Challenge Title";
  private final String description = "Challenge Description";
  private final Unit unit = Unit.MONTHS;
  private final Double targetProgress = 100.0;
  private final Date deadline = date;
  private final int scoreReward = 50;
  private final int scorePenalty = 10;
  private final Visibility visibility = Visibility.PUBLIC;
  private final int version = 1;

  @BeforeEach
  public void setUp() {
    user = mock(User.class);
    challengeDTO = new ChallengeDTO(id, title, description, unit, targetProgress, deadline, scoreReward, scorePenalty,
        user, visibility, version);
  }

  @Test
  public void testDefaultConstructor() {
    ChallengeDTO dto = new ChallengeDTO();
    assertNotNull(dto);
  }

  @Test
  public void testParameterizedConstructor() {
    assertEquals(id, challengeDTO.getId());
    assertEquals(title, challengeDTO.getTitle());
    assertEquals(description, challengeDTO.getDescription());
    assertEquals(unit, challengeDTO.getUnit());
    assertEquals(targetProgress, challengeDTO.getTargetProgress());
    assertEquals(deadline, challengeDTO.getDeadline());
    assertEquals(scoreReward, challengeDTO.getScoreReward());
    assertEquals(scorePenalty, challengeDTO.getScorePenalty());
    assertEquals(user, challengeDTO.getUser());
    assertEquals(visibility, challengeDTO.getChallengeVisibility());
    assertEquals(version, challengeDTO.getVersion());
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

  @Test
  public void testtoString() {
    String expected = "ChallengeDTO [challengeVisibility=" + challengeDTO.getChallengeVisibility() + ", deadline="
        + deadline + ", description="
        + description + ", id=" + id + ", scorePenalty=" + scorePenalty + ", scoreReward=" + scoreReward + ", title="
        + title + ", unit=" + unit + ", user=" + user + ", version=" + version + "]";
    assertEquals(expected, challengeDTO.toString());
  }
}
