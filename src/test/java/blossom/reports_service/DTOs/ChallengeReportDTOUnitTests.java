package blossom.reports_service.DTOs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.validation.*;

import blossom.reports_service.inbound.DTOs.ChallengeReportDTO;
import blossom.reports_service.model.Entities.Challenge;
import blossom.reports_service.model.Entities.ChallengeProgress;
import blossom.reports_service.model.Entities.ChallengeReport;
import blossom.reports_service.model.Entities.User;
import blossom.reports_service.model.Enums.ChallengeStatus;

@ExtendWith(MockitoExtension.class)
public class ChallengeReportDTOUnitTests {

  @Mock
  private Challenge challenge;

  @Mock
  private User user;

  @Mock
  private ChallengeReport challengeReport;

  private final Date date = new Date();

  private final Long id = 1L;
  private List<ChallengeProgress> progressList;
  private final Date startDate = date;
  private final Date endDate = date;
  private final ChallengeStatus status = ChallengeStatus.DONE;
  private final int version = 0;

  private ChallengeReportDTO dto;

  private Validator validator;

  @BeforeEach
  public void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();

    progressList = new ArrayList<>();

    MockitoAnnotations.openMocks(this);

    dto = new ChallengeReportDTO(challenge, user, progressList, startDate, endDate, status, version);
  }

  @Test
  public void testChallengeReportDTODefaultConstructor() {
    ChallengeReportDTO dto = new ChallengeReportDTO();

    assertNotNull(dto);
  }

  @Test
  public void testChallengeReportDTOParameterizedConstructor() {
    ChallengeReportDTO dto = new ChallengeReportDTO(challenge, user, progressList, startDate, endDate,
        status, version);

    assertEquals(challenge, dto.getChallenge());
    assertEquals(user, dto.getUser());
    assertEquals(progressList, dto.getProgressList());
    assertEquals(startDate, dto.getStartDate());
    assertEquals(endDate, dto.getEndDate());
    assertEquals(status, dto.getStatus());
    assertEquals(version, dto.getVersion());
  }

  @Test
  public void testConstructorFromChallengeReport() {
    ChallengeReport challengeReport = new ChallengeReport();
    challengeReport.setId(id);
    challengeReport.setChallenge(challenge);
    challengeReport.setUser(user);
    challengeReport.setProgressList(progressList);
    challengeReport.setStartDate(startDate);
    challengeReport.setEndDate(endDate);
    challengeReport.setStatus(ChallengeStatus.DONE);
    challengeReport.setVersion(version);

    ChallengeReportDTO dto = new ChallengeReportDTO(challengeReport);

    assertEquals(id, dto.getId());
    assertEquals(challenge, dto.getChallenge());
    assertEquals(user, dto.getUser());
    assertEquals(progressList, dto.getProgressList());
    assertEquals(startDate, dto.getStartDate());
    assertEquals(endDate, dto.getEndDate());
    assertEquals(ChallengeStatus.DONE, dto.getStatus());
    assertEquals(version, dto.getVersion());
  }

  @Test
  public void testSettersAndGetters() {
    ChallengeReportDTO dto = new ChallengeReportDTO();
    Challenge newChallenge = new Challenge();
    User newUser = new User();

    dto.setId(2L);
    dto.setChallenge(newChallenge);
    dto.setUser(newUser);
    dto.setProgressList(progressList);
    dto.setStartDate(date);
    dto.setEndDate(date);
    dto.setStatus(ChallengeStatus.DONE);
    dto.setVersion(1);

    assertEquals(2L, dto.getId());
    assertEquals(newChallenge, dto.getChallenge());
    assertEquals(newUser, dto.getUser());
    assertEquals(progressList, dto.getProgressList());
    assertEquals(date, dto.getStartDate());
    assertEquals(date, dto.getEndDate());
    assertEquals(ChallengeStatus.DONE, dto.getStatus());
    assertEquals(1, dto.getVersion());
  }

  @Test
  public void testProgressFunctionality() {
    ChallengeReportDTO dto = new ChallengeReportDTO(challenge, user, progressList, startDate, endDate,
        status, version);

    ChallengeProgress progress1 = mock(ChallengeProgress.class);
    ChallengeProgress progress2 = mock(ChallengeProgress.class);
    ChallengeProgress progress3 = mock(ChallengeProgress.class);
    dto.addProgress(progress1);
    dto.addProgress(progress2);
    dto.addProgress(progress3);

    assertEquals(3, dto.getProgressList().size());
    assertTrue(dto.getProgressList().contains(progress1));
    assertTrue(dto.getProgressList().contains(progress2));
    assertTrue(dto.getProgressList().contains(progress3));

    dto.removeProgress(progress1);

    assertEquals(2, dto.getProgressList().size());
    assertFalse(dto.getProgressList().contains(progress1));
    assertTrue(dto.getProgressList().contains(progress2));
    assertTrue(dto.getProgressList().contains(progress3));

    dto.clearProgress();

    assertEquals(0, dto.getProgressList().size());
    assertFalse(dto.getProgressList().contains(progress2));
    assertFalse(dto.getProgressList().contains(progress3));
  }

  // @Test
  // public void testChallengeReportDTONotNull() {
  // ChallengeReportDTO dto = new ChallengeReportDTO(challenge, user,
  // progressList, startDate, endDate,
  // status, version);

  // Set<ConstraintViolation<ChallengeReportDTO>> violations =
  // validator.validate(dto);
  // assertTrue(violations.isEmpty());

  // dto.setChallenge(null);
  // dto.setUser(null);
  // dto.setProgressList(null); // can be null
  // dto.setStartDate(null);
  // dto.setEndDate(null); // can be null
  // dto.setStatus(null);

  // violations = validator.validate(dto);
  // assertEquals(4, violations.size());

  // for (ConstraintViolation<ChallengeReportDTO> violation : violations) {
  // String propertyPath = violation.getPropertyPath().toString();
  // String message = violation.getMessage();

  // switch (propertyPath) {
  // case "challenge":
  // assertEquals("darf nicht null sein", message);
  // break;
  // case "user":
  // assertEquals("darf nicht null sein", message);
  // break;
  // case "startDate":
  // assertEquals("darf nicht null sein", message);
  // break;
  // case "status":
  // assertEquals("darf nicht null sein", message);
  // break;
  // default:
  // break;
  // }
  // }
}
