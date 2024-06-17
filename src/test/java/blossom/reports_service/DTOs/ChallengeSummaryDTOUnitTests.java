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

  private Long id;
  private Date lastActive;
  private int challengeCount;
  private int doneCount;
  private int pendingCount;
  private int overdueCount;
  private int consecutiveDays;
  private int longestStreak;
  private int version;
  private ChallengeSummaryDTO challengeSummaryDTO;
  private Validator validator;

  @BeforeEach
  public void setUp() {
    lastActive = mock(Date.class);
    MockitoAnnotations.openMocks(this);

    user = mock(User.class);
    challengeSummaryDTO = new ChallengeSummaryDTO(1L, user, lastActive, 10, 5, 3, 2, 7, 14, 1);

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
    assertEquals(1L, challengeSummaryDTO.getId());
    assertEquals(user, challengeSummaryDTO.getUser());
    assertEquals(lastActive, challengeSummaryDTO.getLastActive());
    assertEquals(10, challengeSummaryDTO.getChallengeCount());
    assertEquals(5, challengeSummaryDTO.getDoneCount());
    assertEquals(3, challengeSummaryDTO.getPendingCount());
    assertEquals(2, challengeSummaryDTO.getOverdueCount());
    assertEquals(7, challengeSummaryDTO.getConsecutiveDays());
    assertEquals(14, challengeSummaryDTO.getLongestStreak());
    assertEquals(1, challengeSummaryDTO.getVersion());
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

    challengeSummaryDTO.setUser(newUser);
    challengeSummaryDTO.setLastActive(newLastActive);
    challengeSummaryDTO.setChallengeCount(15);
    challengeSummaryDTO.setDoneCount(8);
    challengeSummaryDTO.setPendingCount(4);
    challengeSummaryDTO.setOverdueCount(1);
    challengeSummaryDTO.setConsecutiveDays(10);
    challengeSummaryDTO.setLongestStreak(20);
    challengeSummaryDTO.setVersion(2);

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

  // @Test
  // public void testNotNull() {
  // System.out.println(challengeSummaryDTO.toString());

  // Set<ConstraintViolation<ChallengeSummaryDTO>> violations =
  // validator.validate(challengeSummaryDTO);

  // assertTrue(violations.isEmpty());

  // challengeSummaryDTO.setUser(null);
  // challengeSummaryDTO.setLastActive(null);

  // violations = validator.validate(challengeSummaryDTO);
  // assertEquals(2, violations.size());
  // }

}
