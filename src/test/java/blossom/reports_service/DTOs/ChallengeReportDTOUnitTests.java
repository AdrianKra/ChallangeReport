package blossom.reports_service.DTOs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Set;

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

  private List<ChallengeProgress> progressList;
  private Date startDate;
  private Date endDate;
  private Long id;
  private int version;
  private Validator validator;
  private ChallengeStatus status;

  @BeforeEach
  public void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();

    MockitoAnnotations.openMocks(this);
    id = 1L;
    user = mock(User.class);
    challenge = mock(Challenge.class);
    progressList = mock(List.class);
    startDate = mock(Date.class);
    endDate = mock(Date.class);
    status = ChallengeStatus.OPEN;
    version = 1;
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
    when(challengeReport.getId()).thenReturn(id);
    when(challengeReport.getChallenge()).thenReturn(challenge);
    when(challengeReport.getUser()).thenReturn(user);
    when(challengeReport.getProgressList()).thenReturn(progressList);
    when(challengeReport.getStartDate()).thenReturn(startDate);
    when(challengeReport.getEndDate()).thenReturn(endDate);
    when(challengeReport.getStatus()).thenReturn(status);
    when(challengeReport.getVersion()).thenReturn(version);

    ChallengeReportDTO dto = new ChallengeReportDTO(challengeReport);

    assertEquals(id, dto.getId());
    assertEquals(challenge, dto.getChallenge());
    assertEquals(user, dto.getUser());
    assertEquals(progressList, dto.getProgressList());
    assertEquals(startDate, dto.getStartDate());
    assertEquals(endDate, dto.getEndDate());
    assertEquals(status, dto.getStatus());
    assertEquals(version, dto.getVersion());
  }

  @Test
  public void testChallengeReportDTONotNull() {
    ChallengeReportDTO dto = new ChallengeReportDTO(challenge, user, progressList, startDate, endDate,
        status, version);

    Set<ConstraintViolation<ChallengeReportDTO>> violations = validator.validate(dto);
    assertTrue(violations.isEmpty());

    dto.setChallenge(null);
    dto.setUser(null);
    dto.setProgressList(null); // can be null
    dto.setStartDate(null);
    dto.setEndDate(null); // can be null
    dto.setStatus(null);

    violations = validator.validate(dto);
    assertEquals(4, violations.size());

    for (ConstraintViolation<ChallengeReportDTO> violation : violations) {
      String propertyPath = violation.getPropertyPath().toString();
      String message = violation.getMessage();

      switch (propertyPath) {
        case "challenge":
          assertEquals("darf nicht null sein", message);
          break;
        case "user":
          assertEquals("darf nicht null sein", message);
          break;
        case "startDate":
          assertEquals("darf nicht null sein", message);
          break;
        case "status":
          assertEquals("darf nicht null sein", message);
          break;
        default:
          break;
      }
    }
  }
}
