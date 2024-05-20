package blossom.reports_service.model;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PersistenceTests {

  @Autowired
  private ChallengeRepository challengeRepository;

  @Autowired
  private ChallengeReportRepository challengeReportRepository;

  @Autowired
  private ChallengeSummaryRepository challengeSummaryRepository;

  @Autowired
  private UserRepository userRepository;

  // Challenge Tests
  @Test
  public void ChallengeFindById() {
    Optional<ChallengeReport> challengeReport = challengeReportRepository.findById(1L);
    Optional<ChallengeReport> challengeReport2 = challengeReportRepository.findById(2L);
    Optional<ChallengeReport> challengeReport3 = challengeReportRepository.findById(3L);
    assert (challengeReport.isPresent());
    assert (challengeReport2.isPresent());
    assert (challengeReport3.isEmpty());
  }

  @Test
  public void ChallengeDeleteById() {
    challengeRepository.deleteById(1L);
    Optional<Challenge> challengeReport = challengeRepository.findById(1L);
    assert (challengeReport.isEmpty());
  }

  // ChallengeReport Tests
  @Test
  public void ReportFindById() {
    Optional<ChallengeReport> challengeReport = challengeReportRepository.findById(1L);
    Optional<ChallengeReport> challengeReport2 = challengeReportRepository.findById(2L);
    Optional<ChallengeReport> challengeReport3 = challengeReportRepository.findById(3L);
    assert (challengeReport.isPresent());
    assert (challengeReport2.isPresent());
    assert (challengeReport3.isEmpty());
  }

  @Test
  public void ReportFindByChallenge() {
    Optional<Challenge> challenge = challengeRepository.findById(1L);
    Optional<ChallengeReport> challengeReport = challengeReportRepository.findByChallenge(challenge.get());
    assert (challengeReport.isPresent());
  }

  @Test
  public void ReportFindByStatus() {
    Iterable<ChallengeReport> challengeReports = challengeReportRepository.findAllByStatus(ChallengeStatus.OPEN);
    assert (challengeReports.iterator().hasNext());
  }

  @Test
  public void ReportDeleteById() {
    challengeReportRepository.deleteById(1L);
    Optional<ChallengeReport> challengeReport = challengeReportRepository.findById(1L);
    assert (challengeReport.isEmpty());
  }

  @Test
  public void ReportSave() {
    Optional<Challenge> challengeOptional = challengeRepository.findById(3L);
    Optional<User> userOptional = userRepository.findById(3L);

    ChallengeReport challengeReport = new ChallengeReport(challengeOptional.get(), userOptional.get(), "Report 3",
        new java.util.Date(), "John Doe", "Report Description");

    challengeReportRepository.save(challengeReport);

    Optional<ChallengeReport> challenge = challengeReportRepository.findById(challengeReport.getId());
    assert (challenge.isPresent());
  }
}
