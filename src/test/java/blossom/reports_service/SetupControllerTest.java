// package blossom.reports_service;

// import static org.mockito.Mockito.when;
// import static org.mockito.Mockito.verify;
// import static org.mockito.ArgumentMatchers.anyLong;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

// import static org.mockito.BDDMockito.given;

// import blossom.reports_service.inbound.Controller.SetupController;
// import blossom.reports_service.inbound.DTOs.ChallengeReportDTO;
// import blossom.reports_service.inbound.Security.JwtValidator;
// import blossom.reports_service.model.Entities.ChallengeReport;
// import blossom.reports_service.model.Services.ReportsService;
// import blossom.reports_service.model.Repositories.ChallengeReportRepository;

// public class SetupControllerTest {

// @Mock
// private ReportsService reportsService;

// @Mock
// private JwtValidator jwtValidator;

// @Mock
// private ChallengeReportRepository ChallengeReportRepository;

// @InjectMocks
// private SetupController setupController;

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
// }
