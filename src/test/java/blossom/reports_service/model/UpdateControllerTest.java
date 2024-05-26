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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.Date;

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
  // @Test
  // public void createChallengeReportTest() throws Exception {
  // challenge = new Challenge("Challenge 1", "Description 1", Unit.KM, 2.0, date,
  // 1, 2, user, Visibility.PUBLIC);
  // challengeReport = new ChallengeReport(challenge, user, "Challenge 1", date,
  // "User 1", "Description 1");
  // var dto = new ReportDTO(1L, 1L, "Challenge 1", date, date, "User 1",
  // "Description 1", ChallengeStatus.OPEN);

  // given(this.reportsService.createChallengeReport(dto)).willReturn(challengeReport);

  // ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
  // String requestJson = ow.writeValueAsString(dto);

  // this.mvc.perform(post("/call/create/{userId}", 1)
  // .contentType(MediaType.APPLICATION_JSON)
  // .content(requestJson))
  // .andDo(print())
  // .andExpect(status().isCreated())
  // .andExpect(jsonPath("$.name").value("Challenge 1"))
  // .andExpect(jsonPath("$.description").value("Description 1"))
  // .andExpect(jsonPath("$.createdBy").value("User 1"));
  // }
}
