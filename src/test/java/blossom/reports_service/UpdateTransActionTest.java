package blossom.reports_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import blossom.reports_service.inbound.ReportDTO;
import blossom.reports_service.model.Entities.Challenge;
import blossom.reports_service.model.Entities.ChallengeReport;
import blossom.reports_service.model.Entities.ChallengeSummary;
import blossom.reports_service.model.Entities.User;
import blossom.reports_service.model.Enums.ChallengeStatus;
import blossom.reports_service.model.Repositories.ChallengeReportRepository;
import blossom.reports_service.model.Repositories.ChallengeRepository;
import blossom.reports_service.model.Repositories.ChallengeSummaryRepository;
import blossom.reports_service.model.Repositories.UserRepository;
import blossom.reports_service.model.Services.ReportsService;

@SpringBootTest
public class UpdateTransActionTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ChallengeRepository challengeRepository;

  @Autowired
  private ChallengeSummaryRepository challengeSummaryRepository;

  @Autowired
  private ChallengeReportRepository challengeReportRepository;

  @Autowired
  private ReportsService reportsService;

  private User user;
  private Challenge challenge;
  private ChallengeReport challengeReport;
  private ReportDTO updateDto;
  private Date startDate;
  private Date endDate;
  private String description;

  @BeforeEach
  public void setUp() {
    user = userRepository.findById(2L).get();
    challenge = challengeRepository.findById(2L).get();
    challengeReport = challengeReportRepository.findById(2L).get();

    startDate = new Date();
    description = "new ChallengeReoport Description";
    updateDto = new ReportDTO(2L, 2L, startDate, null, description, null);
  }

  // Test the updateChallengeReport method, when the status is changed to DONE and
  // was previously OPEN
  @Test
  public void updateChallengeReportTest() {

    ChallengeStatus status = ChallengeStatus.DONE;
    updateDto.setStatus(status);

    // Perform the update
    ChallengeReport updatedChallengeReport = reportsService.updateChallengeReport(updateDto);

    // Verify the updated ChallengeReport
    assertNotNull(updatedChallengeReport);
    assertEquals(updateDto.getUserId(), updatedChallengeReport.getUser().getId());
    assertEquals(updateDto.getChallengeId(), updatedChallengeReport.getChallenge().getId());
    assertTrue(startDate.equals(updatedChallengeReport.getStartDate()));
    assertNotNull(updatedChallengeReport.getEndDate());
    assertEquals(description, updatedChallengeReport.getDescription());
    assertEquals(status, updatedChallengeReport.getStatus());
    assertNotNull(updatedChallengeReport.getEndDate());

    // Verify that the ChallengeReport was updated in the database
    Optional<ChallengeReport> optionalUpdatedChallengeReport = challengeReportRepository
        .findById(updatedChallengeReport.getId());
    assertTrue(optionalUpdatedChallengeReport.isPresent());

    // Verify that the ChallengeSummary was updated
    ChallengeSummary challengeSummary = challengeSummaryRepository.findByUser(user).get();
    assertNotNull(challengeSummary);

    int previousDoneCount = 1;
    int previousPendingCount = 1;
    int previousOverdueCount = 1;

    int expectedDoneCount = previousDoneCount + 1;
    int expectedPendingCount = previousPendingCount - 1;
    int expectedOverdueCount = previousOverdueCount - 1;

    assertEquals(expectedDoneCount, challengeSummary.getDoneCount());
    assertEquals(expectedPendingCount, challengeSummary.getPendingCount());
    assertEquals(expectedOverdueCount, challengeSummary.getOverdueCount());

    // Additional checks for lastActive, consecutiveDays, and longestStreak
    assertNotNull(challengeSummary.getLastActive());
    assertTrue(challengeSummary.getConsecutiveDays() >= 0);
    assertTrue(challengeSummary.getLongestStreak() >= challengeSummary.getConsecutiveDays());
  }

  // Test the updateChallengeReport method, when the status is changed to OVERDUE
  // from OPEN
  @Test
  public void updateChallengeReportTest2() {

    ChallengeStatus status = ChallengeStatus.OPEN;
    updateDto.setStatus(status);

    // Perform the update
    ChallengeReport updatedChallengeReport = reportsService.updateChallengeReport(updateDto);

    // Verify the updated ChallengeReport
    assertNotNull(updatedChallengeReport);
    assertEquals(updateDto.getUserId(), updatedChallengeReport.getUser().getId());
    assertEquals(updateDto.getChallengeId(), updatedChallengeReport.getChallenge().getId());
    assertTrue(startDate.equals(updatedChallengeReport.getStartDate()));
    assertNull(updatedChallengeReport.getEndDate());
    assertEquals(description, updatedChallengeReport.getDescription());
    assertEquals(ChallengeStatus.OVERDUE, updatedChallengeReport.getStatus());

    Optional<ChallengeReport> optionalUpdatedChallengeReport = challengeReportRepository
        .findById(updatedChallengeReport.getId());
    assertTrue(optionalUpdatedChallengeReport.isPresent());

    // Verify that the ChallengeSummary was updated
    ChallengeSummary challengeSummary = challengeSummaryRepository.findByUser(user).get();
    assertNotNull(challengeSummary);

    int previousDoneCount = 1;
    int previousPendingCount = 1;
    int previousOverdueCount = 1;

    int expectedDoneCount = previousDoneCount;
    int expectedPendingCount = previousPendingCount;
    int expectedOverdueCount = previousOverdueCount + 1;

    assertEquals(expectedDoneCount, challengeSummary.getDoneCount());
    assertEquals(expectedPendingCount, challengeSummary.getPendingCount());
    assertEquals(expectedOverdueCount, challengeSummary.getOverdueCount());

    // Additional checks for lastActive, consecutiveDays, and longestStreak
    assertNotNull(challengeSummary.getLastActive());
    assertTrue(challengeSummary.getConsecutiveDays() >= 0);
    assertTrue(challengeSummary.getLongestStreak() >= challengeSummary.getConsecutiveDays());
  }

  // Test the updateChallengeReport method, when the status is changed to DONE
  // from OVERDUE
  @Test
  public void updateChallengeReportTest3() {

    user = userRepository.findById(3L).get();
    challenge = challengeRepository.findById(3L).get();
    challengeReport = challengeReportRepository.findById(3L).get();

    ChallengeStatus status = ChallengeStatus.DONE;
    updateDto.setStatus(status);

    // Perform the update
    ChallengeReport updatedChallengeReport = reportsService.updateChallengeReport(updateDto);

    // Verify the updated ChallengeReport
    assertNotNull(updatedChallengeReport);
    assertEquals(updateDto.getUserId(), updatedChallengeReport.getUser().getId());
    assertEquals(updateDto.getChallengeId(), updatedChallengeReport.getChallenge().getId());
    assertTrue(startDate.equals(updatedChallengeReport.getStartDate()));
    assertNotNull(updatedChallengeReport.getEndDate());
    assertEquals(description, updatedChallengeReport.getDescription());
    assertEquals(status, updatedChallengeReport.getStatus());
    assertNotNull(updatedChallengeReport.getEndDate());

    Optional<ChallengeReport> optionalUpdatedChallengeReport = challengeReportRepository
        .findById(updatedChallengeReport.getId());
    assertTrue(optionalUpdatedChallengeReport.isPresent());

    // Verify that the ChallengeSummary was updated
    ChallengeSummary challengeSummary = challengeSummaryRepository.findByUser(user).get();
    assertNotNull(challengeSummary);

    int previousDoneCount = 2;
    int previousPendingCount = 2;
    int previousOverdueCount = 2;

    int expectedDoneCount = previousDoneCount + 1;
    int expectedPendingCount = previousPendingCount - 1;
    int expectedOverdueCount = previousOverdueCount - 1;

    assertEquals(expectedDoneCount, challengeSummary.getDoneCount());
    assertEquals(expectedPendingCount, challengeSummary.getPendingCount());
    assertEquals(expectedOverdueCount, challengeSummary.getOverdueCount());

    // Additional checks for lastActive, consecutiveDays, and longestStreak
    assertNotNull(challengeSummary.getLastActive());
    assertTrue(challengeSummary.getConsecutiveDays() >= 0);
    assertTrue(challengeSummary.getLongestStreak() >= challengeSummary.getConsecutiveDays());
  }
}
