package blossom.reports_service.model;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import blossom.reports_service.inbound.ReportDTO;
import blossom.reports_service.inbound.UpdateController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.Date;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(UpdateController.class)
public class UpdateControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ReportsService reportsService;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private ChallengeRepository challengeRepository;

  private User user;
  private Challenge challenge;
  private ChallengeReport challengeReport;
  private ChallengeSummary challengeSummary;

  java.util.Date date = new Date();

  @BeforeEach
  public void setUp() {
    user = new User();
  }

  // Test for createChallengeReport with status code 201
  @Test
  public void createChallengeReportTest() throws Exception {
    challenge = new Challenge("Challenge 1", "Description 1", Unit.KM, 2.0, date,
        1, 2, user, Visibility.PUBLIC);
    challengeReport = new ChallengeReport(challenge, user, date, "Description 1");
    var dto = new ReportDTO(1L, 1L, date, date, "Description 1", ChallengeStatus.OPEN);

    given(this.reportsService.createChallengeReport(any(ReportDTO.class)))
        .willReturn(challengeReport);

    ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(dto);

    this.mvc.perform(post("/call/createChallengeReport")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.challenge.title").value("Challenge 1"))
        .andExpect(jsonPath("$.challenge.description").value("Description 1"))
        .andExpect(jsonPath("$.challenge.user.id").isEmpty())
        .andExpect(jsonPath("$.description").value("Description 1"))
        .andExpect(jsonPath("$.status").value("OPEN"));
  }
}
