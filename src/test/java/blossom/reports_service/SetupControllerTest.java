package blossom.reports_service;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import blossom.reports_service.inbound.Controller.SetupController;
import blossom.reports_service.inbound.Security.JwtValidator;
import blossom.reports_service.model.Repositories.ChallengeReportRepository;
import blossom.reports_service.model.Services.ReportsService;

public class SetupControllerTest {
    @Mock
    private ReportsService reportsService;

    @Mock
    private JwtValidator jwtValidator;

    @Mock
    private ChallengeReportRepository ChallengeReportRepository;

    @InjectMocks
    private SetupController setupController;

    // TODO: die Funktionen gibt es alle überhaupt nicht, wieso soll das getestet
    // werden?
    // @Test
    // public void testSaveChallengeReport() {
    // ChallengeReportDTO challengeReportDTO = new ChallengeReportDTO();
    // ChallengeReport challengeReport = new ChallengeReport();

    // given(ChallengeReportRepository.save(challengeReport)).thenReturn(challengeReport);

    // ResponseEntity<ChallengeReportDTO> response =
    // setupController.save(challengeReportDTO);

    // verify(reportsService).saveChallengeReport(challengeReportDTO);
    // }

    // @Test
    // public void testDeleteChallengeReport() {
    // ResponseEntity<Void> response = setupController.deleteChallengeReport(1L);

    // verify(reportsService).deleteChallengeReport(anyLong());
    // }

    // @Test
    // public void testCreateChallengeReport(){
    // //TODO: hier muss nur ein valider JWT mit einer User-Mail übergeben werden.
    // Das sollte easy sein
    // setupController.createChallengeSummary(null);
    // }
}
