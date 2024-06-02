package blossom.reports_service.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import blossom.reports_service.model.Entities.ChallengeReport;
import blossom.reports_service.model.Entities.ChallengeSummary;
import blossom.reports_service.model.Repositories.ChallengeReportRepository;
import blossom.reports_service.model.Repositories.ChallengeSummaryRepository;
import blossom.reports_service.model.Services.ReportsService;

@SpringBootTest
public class DeleteTransActionTest {

  private static final Long OPEN_REPORT_ID = 1L;
  private static final Long DONE_REPORT_ID = 2L;
  private static final Long OVERDUE_REPORT_ID = 3L;

  @Autowired
  private ChallengeSummaryRepository challengeSummaryRepository;

  @Autowired
  private ChallengeReportRepository challengeReportRepository;

  @Autowired
  private ReportsService reportsService;

  @BeforeEach
  public void setUp() {
    // Set up test data
  }

  @Test
  public void deleteChallengeReportOpenTest() {
    deleteChallengeReportAndAssert(OPEN_REPORT_ID, 0, 0, 0,
        0, 0);
  }

  @Test
  public void deleteChallengeReportDoneTest() {
    deleteChallengeReportAndAssert(DONE_REPORT_ID, 0, 0, 0,
        1, 1);
  }

  @Test
  public void deleteChallengeReportOverdueTest() {
    deleteChallengeReportAndAssert(OVERDUE_REPORT_ID, 1, 2, 1,
        1, 2);
  }

  private void deleteChallengeReportAndAssert(Long reportId, int challengeCount, int doneCount, int pendingCount,
      int overdueCount, int consecutiveDays) {
    Optional<ChallengeReport> challengeReportOptional = challengeReportRepository.findById(reportId);
    assertTrue(challengeReportOptional.isPresent());

    ChallengeSummary challengeSummary = challengeSummaryRepository.findById(reportId).get();
    Date lastActive = challengeSummary.getLastActive();

    reportsService.deleteChallengeReport(reportId);

    Optional<ChallengeReport> deletedChallengeReport = challengeReportRepository.findById(reportId);
    assertTrue(deletedChallengeReport.isEmpty());

    ChallengeSummary updatedChallengeSummary = challengeSummaryRepository.findById(reportId).get();
    assertEquals(challengeCount, updatedChallengeSummary.getChallengeCount());
    assertEquals(doneCount, updatedChallengeSummary.getDoneCount());
    assertEquals(pendingCount, updatedChallengeSummary.getPendingCount());
    assertEquals(overdueCount, updatedChallengeSummary.getOverdueCount());
    assertEquals(consecutiveDays, updatedChallengeSummary.getConsecutiveDays());
    assertEquals(lastActive, updatedChallengeSummary.getLastActive());
  }
}
