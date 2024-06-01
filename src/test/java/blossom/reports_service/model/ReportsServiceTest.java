package blossom.reports_service.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import blossom.reports_service.inbound.ReportDTO;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ReportsServiceTest {

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
  private ChallengeSummary challengeSummary;
  private ReportDTO dto;

  @BeforeEach
  public void setUp() {
    user = new User("new@email.de");
    challenge = new Challenge("new Challenge name", "new challenge Description", Unit.KM, 2.0, new Date(), 1,
        2, user, Visibility.FRIENDS);
    challengeReport = new ChallengeReport(challenge, user, new Date(), "new ChallengeReoport Description");
    dto = new ReportDTO(4L, 5L, new Date(), new Date(), "new DTO Description", ChallengeStatus.DONE);

    // Persist entities to the database
    challengeSummary = challengeSummaryRepository.save(new ChallengeSummary(user));
    challengeRepository.save(challenge);
    userRepository.save(user);
  }

  @Test
  public void createChallengeReportTest() {
    ChallengeReport createdChallengeReport = reportsService.createChallengeReport(dto);

    // Überprüfen Sie, ob das erstellte ChallengeReport die erwarteten Werte hat
    assertEquals(dto.getUserId(), createdChallengeReport.getUser().getId());
    assertEquals(dto.getChallengeId(), createdChallengeReport.getChallenge().getId());

    // Überprüfen Sie, ob das ChallengeReport in der Datenbank gespeichert wurde
    Optional<ChallengeReport> optionalChallengeReport = challengeReportRepository
        .findByChallengeIdAndUserId(dto.getChallengeId(), dto.getUserId());
    assertTrue(optionalChallengeReport.isPresent());

    ChallengeReport challengeReportFromDb = optionalChallengeReport.get();
    assertEquals(createdChallengeReport.getId(), challengeReportFromDb.getId());

    assertNotNull(challengeSummaryRepository.findByUser(user).get());

    assertEquals(challengeSummaryRepository.findAll().size(), 4);

    // Überprüfen Sie, ob die ChallengeSummary aktualisiert wurde
    ChallengeSummary challengeSummary = challengeSummaryRepository.findByUser(user).get();
    assertEquals(1, challengeSummary.getChallengeCount());
    assertEquals(1, challengeSummary.getPendingCount());
  }

  @Test
  public void getChallengeSummaryTest() {
    // Rufen Sie die getChallengeSummary Methode auf
    ChallengeSummary returnedChallengeSummary = reportsService.getChallengeSummary(user.getId());

    // Überprüfen Sie, ob die zurückgegebene ChallengeSummary die erwarteten Werte
    // hat
    assertEquals(challengeSummary.getId(), returnedChallengeSummary.getId());
    assertEquals(challengeSummary.getUser().getId(), returnedChallengeSummary.getUser().getId());
    // ...

    // Überprüfen Sie, ob die ChallengeSummary in der Datenbank vorhanden ist
    Optional<ChallengeSummary> optionalChallengeSummary = challengeSummaryRepository.findByUser(user);
    assertTrue(optionalChallengeSummary.isPresent());

    ChallengeSummary challengeSummaryFromDb = optionalChallengeSummary.get();
    assertEquals(challengeSummary.getId(), challengeSummaryFromDb.getId());
  }
}