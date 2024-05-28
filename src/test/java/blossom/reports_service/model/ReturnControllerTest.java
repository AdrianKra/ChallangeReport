package blossom.reports_service.model;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import blossom.reports_service.inbound.ReturnController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

// for every controller
@WebMvcTest(ReturnController.class)
public class ReturnControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private ReportsService reportsService;

  @MockBean
  private UserRepository userRepository;

  private User user;
  private Challenge challenge;
  private ChallengeReport challengeReport;
  private ChallengeSummary challengeSummary;

  java.util.Date date = new Date();

  @BeforeEach
  public void setUp() {
    user = new User("example@org.de");
  }

  // Test for getChallengeReports with status code 200
  @Test
  public void getChallengeReportsTest() throws Exception {
    challenge = new Challenge("Challenge 1", "Description 1", Unit.KM, 2.0, date, 1, 2, user, Visibility.PUBLIC);
    challengeReport = new ChallengeReport(challenge, user, date, "Description 1");

    given(this.reportsService.getChallengeReports(1L)).willReturn(Collections.singletonList(challengeReport));
    this.mvc.perform(get("/report/list/{userId}", 1))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].description").value("Description 1"));
  }

  // Test for getChallengeReports with status code 404 because of the User
  @Test
  public void getChallengeReportsNotFoundTest() throws Exception {
    given(this.reportsService.getChallengeReports(2L)).willThrow(new NotFoundException("User not found"));
    this.mvc.perform(get("/report/list/{userId}", 2))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  // Test for getChallengeReports with status code 404 because of the
  // ChallengeReport
  @Test
  public void getChallengeReportsNotFoundChallengeReportTest() throws Exception {
    given(this.reportsService.getChallengeReports(1L)).willThrow(new NotFoundException("ChallengeReport not found"));
    this.mvc.perform(get("/report/list/{userId}", 1))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  // Test for getChallengeSummary with status code 200
  @Test
  public void getSummaryTest() throws Exception {
    challengeSummary = new ChallengeSummary(user);

    given(this.reportsService.getChallengeSummary(1L)).willReturn(challengeSummary);
    this.mvc.perform(get("/report/summary/{userId}", 1))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.lastActive").value(Matchers.nullValue()))
        .andExpect(jsonPath("$.challengeCount").value(0))
        .andExpect(jsonPath("$.doneCount").value(0))
        .andExpect(jsonPath("$.pendingCount").value(0))
        .andExpect(jsonPath("$.overdueCount").value(0))
        .andExpect(jsonPath("$.consecutiveDays").value(0))
        .andExpect(jsonPath("$.longestStreak").value(0));
  }

  // Test for getChallengeSummary with status code 404 because of the User
  @Test
  public void getSummaryNotFoundTest() throws Exception {
    given(this.reportsService.getChallengeSummary(2L)).willThrow(new NotFoundException("User not found"));
    this.mvc.perform(get("/report/summary/{userId}", 2))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  // Test for getChallengeSummary with status code 404 because of the
  // ChallengeSummary
  @Test
  public void getSummaryNotFoundChallengeSummaryTest() throws Exception {
    given(this.reportsService.getChallengeSummary(1L)).willThrow(new NotFoundException("ChallengeSummary not found"));
    this.mvc.perform(get("/report/summary/{userId}", 1))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void testGetQuotes() throws Exception {
    // Mock the behavior of the ReportsService
    when(reportsService.getQuotes(anyString()))
        .thenReturn(Collections.singletonList(new Quote("Test quote", "Test author")));

    // Perform GET request to /report/quote/{category}
    this.mvc.perform(get("/report/quote/happiness"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].quote").value("Test quote"))
        .andExpect(jsonPath("$[0].author").value("Test author"));
  }

}
