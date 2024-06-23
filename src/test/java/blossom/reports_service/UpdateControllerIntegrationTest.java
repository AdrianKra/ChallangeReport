package blossom.reports_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import blossom.reports_service.inbound.Controller.UpdateController;
import blossom.reports_service.inbound.Security.JwtValidator;
import blossom.reports_service.inbound.Security.SecurityConfig;
import blossom.reports_service.model.Role;
import blossom.reports_service.model.Entities.Challenge;
import blossom.reports_service.model.Entities.ChallengeReport;
import blossom.reports_service.model.Entities.User;
import blossom.reports_service.model.Enums.ChallengeStatus;
import blossom.reports_service.model.Services.ReportsService;
import jakarta.servlet.http.HttpServletRequest;

@WebMvcTest(controllers = UpdateController.class)
@Import(SecurityConfig.class)
public class UpdateControllerIntegrationTest {

        @Autowired
        private MockMvc mvc;

        @MockBean
        private ReportsService reportsService;

        @MockBean
        private JwtValidator jwtValidator;

        @Autowired
        private ObjectMapper objectMapper;

        private final String AUTH_HEADER = "Bearer ANY-JWT-STRING";
        private final String TEST_USER_EMAIL = "harry@hacker.de";

        @BeforeEach
        public void setUp() throws Exception {
                UserDetails userDetails = org.springframework.security.core.userdetails.User
                                .withUsername(TEST_USER_EMAIL)
                                .password("***")
                                .authorities(Role.USER.getAuthority())
                                .build();

                given(jwtValidator.isValidJWT(any(String.class))).willReturn(true);
                given(jwtValidator.getUserEmail(any(String.class))).willReturn(TEST_USER_EMAIL);
                given(jwtValidator.resolveToken(any(HttpServletRequest.class))).willReturn(AUTH_HEADER.substring(7));
                given(jwtValidator.getAuthentication(any(String.class)))
                                .willReturn(new UsernamePasswordAuthenticationToken(userDetails, "",
                                                userDetails.getAuthorities()));
        }

        @Test
        public void testCreateChallengeReport() throws Exception {
                User user = new User();
                Challenge challenge = new Challenge();

                Date startDate = new Date();
                Date endDate = new Date(startDate.getTime() + 1000);

                ChallengeReport challengeReport = new ChallengeReport();
                challengeReport.setId(1L);
                challengeReport.setChallenge(challenge);
                challengeReport.setUser(user);
                challengeReport.setStartDate(startDate);
                challengeReport.setEndDate(endDate);
                challengeReport.setStatus(ChallengeStatus.DONE);
                challengeReport.setVersion(1);

                given(reportsService.createChallengeReport(any(String.class), any(Long.class)))
                                .willReturn(challengeReport);

                this.mvc.perform(post("/rest/call/createChallengeReport/{challengeId}/{userId}", 1L, 1L)
                                .header("Authorization", AUTH_HEADER))
                                .andDo(print())
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").value(1L))
                                .andExpect(jsonPath("$.challenge").isNotEmpty())
                                .andExpect(jsonPath("$.user").isNotEmpty())
                                .andExpect(jsonPath("$.startDate").isNotEmpty())
                                .andExpect(jsonPath("$.endDate").isNotEmpty())
                                .andExpect(jsonPath("$.status").value(ChallengeStatus.DONE.toString()))
                                .andExpect(jsonPath("$.version").value(1));
        }

        // @Test
        // @WithMockUser(authorities = "ADMIN")
        // void testCreateChallengeReport2() throws Exception {
        // this.mvc.perform(post("/rest/call/createChallengeReport/{challengeId}/{userId}",
        // 1L, 1L)
        // .header("Authorization", "Bearer valid-token"))
        // .andExpect(status().isCreated())
        // .andExpect(jsonPath("$.id", is(notNullValue())));
        // }

        // @Test
        // @WithMockUser(authorities = "ADMIN")
        // void testDeleteReport() throws Exception {
        // this.mvc.perform(delete("/rest/call/deleteChallengeReport/{challengeId}", 1L)
        // .header("Authorization", "Bearer valid-token"))
        // .andExpect(status().isOk());
        // }

}
