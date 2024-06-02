package blossom.reports_service;

import blossom.reports_service.inbound.Controller.ReturnController;
import blossom.reports_service.model.Quote;
import blossom.reports_service.model.Entities.Challenge;
import blossom.reports_service.model.Entities.ChallengeReport;
import blossom.reports_service.model.Entities.ChallengeSummary;
import blossom.reports_service.model.Entities.User;
import blossom.reports_service.model.Enums.Unit;
import blossom.reports_service.model.Enums.Visibility;
import blossom.reports_service.model.Exceptions.NotFoundException;
import blossom.reports_service.model.Repositories.ChallengeSummaryRepository;
import blossom.reports_service.model.Repositories.UserRepository;
import blossom.reports_service.model.Services.ReportsService;
import blossom.reports_service.model.Services.RetryableServiceClient;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

// for every controller
@WebMvcTest(ReturnController.class)
public class ReturnControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private ReportsService reportsService;

  @MockBean
  private RetryableServiceClient quotesService;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private ChallengeSummaryRepository challengeSummaryRepository;

  private final String email = "example@org.de";

  private User user;
  private Challenge challenge;
  private ChallengeReport challengeReport;
  private ChallengeSummary challengeSummary;

  java.util.Date date = new Date();

  @BeforeEach
  public void setUp() {
    user = new User(email);
    user.setId(1L);
  }

  // Test for getChallengeReports with status code 200
  @Test
  public void getChallengeReportsTest() throws Exception {
    challenge = new Challenge("Challenge 1", "Description 1", Unit.KM, 2.0, date, 1,
        2, user, Visibility.PUBLIC);
    challenge.setId(1L);

    challengeReport = new ChallengeReport(challenge, user, null, "Description 1");
    challengeReport.setId(1L);

    given(this.reportsService.getChallengeReports(1L)).willReturn(Collections.singletonList(challengeReport));
    this.mvc.perform(get("/rest/report/list/{userId}", 1))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].challenge").exists())
        .andExpect(jsonPath("$[0].challenge.id").value(1))
        .andExpect(jsonPath("$[0].challenge.title").value("Challenge 1"))
        .andExpect(jsonPath("$[0].challenge.description").value("Description 1"))
        .andExpect(jsonPath("$[0].challenge.unit").value("KM"))
        .andExpect(jsonPath("$[0].challenge.targetProgress").value(2.0))
        .andExpect(jsonPath("$[0].challenge.deadline", Matchers.matchesPattern(
            "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(.\\d+)?(Z|[+-]\\d{2}:\\d{2})")))
        .andExpect(jsonPath("$[0].challenge.scoreReward").value(1))
        .andExpect(jsonPath("$[0].challenge.scorePenalty").value(2))
        .andExpect(jsonPath("$[0].challenge.user").exists())
        .andExpect(jsonPath("$[0].challenge.user.id").value(1))
        .andExpect(jsonPath("$[0].challenge.user.email").value(email))
        .andExpect(jsonPath("$[0].challenge.challengeVisibility").value("PUBLIC"))
        .andExpect(jsonPath("$[0].user.id").value(1))
        .andExpect(jsonPath("$[0].user.email").value(email))
        .andExpect(jsonPath("$[0].startDate").doesNotExist())
        .andExpect(jsonPath("$[0].endDate").doesNotExist())
        .andExpect(jsonPath("$[0].description").value("Description 1"))
        .andExpect(jsonPath("$[0].progress").value(0.0))
        .andExpect(jsonPath("$[0].status").value("OPEN"));
  }

  // Test for getChallengeReports with status code 404 because of the User
  @Test
  public void getChallengeReportsNotFoundTest() throws Exception {
    given(this.reportsService.getChallengeReports(2L)).willThrow(new NotFoundException("User not found"));
    this.mvc.perform(get("/rest/report/list/{userId}", 2))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  // Test for getChallengeReports with status code 404 because of the
  // ChallengeReport
  @Test
  public void getChallengeReportsNotFoundChallengeReportTest() throws Exception {
    given(this.reportsService.getChallengeReports(1L)).willThrow(new NotFoundException("ChallengeReport not found"));
    this.mvc.perform(get("/rest/report/list/{userId}", 1))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  // Test for getChallengeSummary with status code 200
  @Test
  public void getSummaryTest() throws Exception {
    challengeSummary = new ChallengeSummary(user);
    challengeSummary.setId(1L);

    given(this.reportsService.getChallengeSummary(1L)).willReturn(challengeSummary);
    this.mvc.perform(get("/rest/report/summary/{userId}", 1))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.user").exists())
        .andExpect(jsonPath("$.user.id").value(1))
        .andExpect(jsonPath("$.user.email").value(email))
        .andExpect(jsonPath("$.lastActive", Matchers.matchesPattern(
            "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(.\\d+)?(Z|[+-]\\d{2}:\\d{2})")))
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
    this.mvc.perform(get("/rest/report/summary/{userId}", 2))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  // Test for getChallengeSummary with status code 404 because of the
  // ChallengeSummary
  @Test
  public void getSummaryNotFoundChallengeSummaryTest() throws Exception {
    given(this.reportsService.getChallengeSummary(1L)).willThrow(new NotFoundException("ChallengeSummary not found"));
    this.mvc.perform(get("/rest/report/summary/{userId}", 1))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void testGetQuotes() throws Exception {
    // Mock the behavior of the ReportsService
    when(quotesService.getQuotes(anyString()))
        .thenReturn(new Quote[] { new Quote("Test quote", "Test author") });

    // Perform GET request to /report/quote/{category}
    this.mvc.perform(get("/rest/report/quote/happiness"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].quote").value("Test quote"))
        .andExpect(jsonPath("$[0].author").value("Test author"));
  }

}
