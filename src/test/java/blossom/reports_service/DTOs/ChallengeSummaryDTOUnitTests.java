package blossom.reports_service.DTOs;

import blossom.reports_service.inbound.DTOs.ChallengeSummaryDTO;
import blossom.reports_service.model.Entities.Challenge;
import blossom.reports_service.model.Entities.ChallengeSummary;
import blossom.reports_service.model.Entities.User;
import blossom.reports_service.model.Enums.ChallengeStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ChallengeSummaryDTOUnitTests {

  @Mock
  private ChallengeSummary challengeSummary;

  @Mock
  private User user;

  @Mock
  private Challenge challenge;

  private final Date date = new Date();

  private final Long id = 1L;
  private final Date lastActive = date;
  private final int challengeCount = 10;
  private final int doneCount = 5;
  private final int pendingCount = 3;
  private final int overdueCount = 2;
  private final int consecutiveDays = 7;
  private final int longestStreak = 14;
  private final int version = 1;

  private ChallengeSummaryDTO challengeSummaryDTO;

  private Validator validator;

  @BeforeEach
  public void setUp() {
    challengeSummaryDTO = new ChallengeSummaryDTO(id, user, lastActive, challengeCount, doneCount, pendingCount,
        overdueCount, consecutiveDays, longestStreak, version);

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();

  }

  @Test
  public void testDefaultConstructor() {
    ChallengeSummaryDTO dto = new ChallengeSummaryDTO();
    assertNotNull(dto);
  }

  @Test
  public void testParameterizedConstructor() {
    assertEquals(id, challengeSummaryDTO.getId());
    assertEquals(user, challengeSummaryDTO.getUser());
    assertEquals(lastActive, challengeSummaryDTO.getLastActive());
    assertEquals(challengeCount, challengeSummaryDTO.getChallengeCount());
    assertEquals(doneCount, challengeSummaryDTO.getDoneCount());
    assertEquals(pendingCount, challengeSummaryDTO.getPendingCount());
    assertEquals(overdueCount, challengeSummaryDTO.getOverdueCount());
    assertEquals(consecutiveDays, challengeSummaryDTO.getConsecutiveDays());
    assertEquals(longestStreak, challengeSummaryDTO.getLongestStreak());
    assertEquals(version, challengeSummaryDTO.getVersion());
  }

  @Test
  public void testConstructorFromChallengeSummary() {
    ChallengeSummary challengeSummary = new ChallengeSummary();
    challengeSummary.setUser(user);
    challengeSummary.setLastActive(lastActive);
    challengeSummary.setChallengeCount(10);
    challengeSummary.setDoneCount(5);
    challengeSummary.setPendingCount(3);
    challengeSummary.setOverdueCount(2);
    challengeSummary.setConsecutiveDays(7);
    challengeSummary.setLongestStreak(14);
    challengeSummary.setVersion(1);

    ChallengeSummaryDTO dto = new ChallengeSummaryDTO(challengeSummary);

    assertEquals(user, dto.getUser());
    assertEquals(lastActive, dto.getLastActive());
    assertEquals(10, dto.getChallengeCount());
    assertEquals(5, dto.getDoneCount());
    assertEquals(3, dto.getPendingCount());
    assertEquals(2, dto.getOverdueCount());
    assertEquals(7, dto.getConsecutiveDays());
    assertEquals(14, dto.getLongestStreak());
    assertEquals(1, dto.getVersion());
  }

  @Test
  public void testSettersAndGetters() {

    User newUser = mock(User.class);
    Date newLastActive = mock(Date.class);

    challengeSummaryDTO.setId(2L);
    challengeSummaryDTO.setUser(newUser);
    challengeSummaryDTO.setLastActive(newLastActive);
    challengeSummaryDTO.setChallengeCount(15);
    challengeSummaryDTO.setDoneCount(8);
    challengeSummaryDTO.setPendingCount(4);
    challengeSummaryDTO.setOverdueCount(1);
    challengeSummaryDTO.setConsecutiveDays(10);
    challengeSummaryDTO.setLongestStreak(20);
    challengeSummaryDTO.setVersion(2);

    assertEquals(2L, challengeSummaryDTO.getId());
    assertEquals(newUser, challengeSummaryDTO.getUser());
    assertEquals(newLastActive, challengeSummaryDTO.getLastActive());
    assertEquals(15, challengeSummaryDTO.getChallengeCount());
    assertEquals(8, challengeSummaryDTO.getDoneCount());
    assertEquals(4, challengeSummaryDTO.getPendingCount());
    assertEquals(1, challengeSummaryDTO.getOverdueCount());
    assertEquals(10, challengeSummaryDTO.getConsecutiveDays());
    assertEquals(20, challengeSummaryDTO.getLongestStreak());
    assertEquals(2, challengeSummaryDTO.getVersion());
  }

  @Test
  public void testToString() {
    String expected = "ChallengeSummaryDTO [challengeCount=" + challengeCount + ", consecutiveDays=" + consecutiveDays
        + ", doneCount=" + doneCount + ", id=" + id + ", lastActive=" + lastActive + ", longestStreak="
        + longestStreak + ", overdueCount=" + overdueCount + ", pendingCount=" + pendingCount + ", user=" + user
        + ", version=" + version + "]";
    assertEquals(expected, challengeSummaryDTO.toString());
  }
}
