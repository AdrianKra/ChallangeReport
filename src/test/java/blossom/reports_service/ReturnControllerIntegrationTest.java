// package blossom.reports_service;

// import blossom.reports_service.inbound.Controller.ReturnController;
// import blossom.reports_service.inbound.DTOs.ChallengeReportDTO;
// import blossom.reports_service.inbound.Security.JwtValidator;
// import blossom.reports_service.model.Entities.ChallengeReport;
// import blossom.reports_service.model.Services.ReportsService;
// import blossom.reports_service.model.Services.RetryableServiceClient;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import
// org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.test.web.servlet.MockMvc;
// import jakarta.servlet.http.HttpServletRequest;

// import java.util.List;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.BDDMockito.given;
// import static
// org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static org.hamcrest.Matchers.hasSize;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest(ReturnController.class)
// public class ReturnControllerIntegrationTest {

// @Autowired
// private MockMvc mvc;

// @MockBean
// private ReportsService reportsService;

// @MockBean
// private RetryableServiceClient quotesService;

// @MockBean
// private JwtValidator jwtValidator;

// @Autowired
// private ObjectMapper objectMapper;

// private final String AUTH_HEADER = "Bearer ANY-JWT-STRING";
// private final String TEST_USER_EMAIL = "harry@hacker.de";

// @BeforeEach
// public void setUp() throws Exception {
// UserDetails userDetails =
// org.springframework.security.core.userdetails.User.withUsername(TEST_USER_EMAIL)
// .password("***")
// .authorities("USER", "ADMIN")
// .build();

// given(jwtValidator.isValidJWT(any(String.class))).willReturn(true);
// given(jwtValidator.getUserEmail(any(String.class))).willReturn(TEST_USER_EMAIL);
// given(jwtValidator.resolveToken(any(HttpServletRequest.class))).willReturn(AUTH_HEADER.substring(7));
// given(jwtValidator.getAuthentication(any(String.class)))
// .willReturn(new UsernamePasswordAuthenticationToken(userDetails, "",
// userDetails.getAuthorities()));
// }

// @Test
// public void testGetAllChallengeReports() throws Exception {
// Iterable<ChallengeReport> mockReports = List.of(new ChallengeReport());
// given(reportsService.getChallengeReports(TEST_USER_EMAIL)).willReturn(mockReports);

// this.mvc.perform(get("/rest/report/challenges")
// .header("Authorization", AUTH_HEADER))
// .andDo(print())
// .andExpect(status().isOk())
// .andExpect(jsonPath("$", hasSize(1)));
// }

// @Test
// public void testGetChallengeSummary() throws Exception {
// ChallengeReportDTO mockSummary = new ChallengeReportDTO();
// given(reportsService.getChallengeSummary(1L)).willReturn(mockSummary);

// this.mvc.perform(get("/rest/report/summary/{challengeId}", 1L)
// .header("Authorization", AUTH_HEADER))
// .andDo(print())
// .andExpect(status().isOk())
// .andExpect(jsonPath("$.id").value(1L));
// }
// }
