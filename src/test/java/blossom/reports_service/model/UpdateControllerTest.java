package blossom.reports_service.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import blossom.reports_service.inbound.ReportDTO;
import blossom.reports_service.inbound.UpdateController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Date;
import org.mockito.BDDMockito;

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
    private ReportDTO dto;

    java.util.Date date = new Date();

    @BeforeEach
    public void setUp() {
        user = new User("example@org.de");
        challenge = new Challenge("Challenge 1", "Description 1", Unit.KM, 2.0, date,
                1, 2, user, Visibility.PUBLIC);
        challengeReport = new ChallengeReport(challenge, user, date, "Description 1");
        dto = new ReportDTO(1L, 1L, date, date, "Description 1", ChallengeStatus.OPEN);
    }

    // Test for createChallengeReport with status code 201
    @Test
    public void createChallengeReportTest() throws Exception {

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

    // Test for createChallengeReport with status code 404 because of
    // NotFoundException of challenge
    @Test
    public void createChallengeReportBadRequestTest() throws Exception {
        given(this.reportsService.createChallengeReport(any(ReportDTO.class)))
                .willThrow(new NotFoundException("Challenge not found"));

        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(dto);

        this.mvc.perform(post("/call/createChallengeReport")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // Test for createChallengeReport with status code 404 because of
    // NotFoundException of user
    @Test
    public void createChallengeReportBadRequestTest2() throws Exception {
        given(this.reportsService.createChallengeReport(any(ReportDTO.class)))
                .willThrow(new NotFoundException("User not found"));

        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(dto);

        this.mvc.perform(post("/call/createChallengeReport")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // Test for createChallengeReport with status code 409 because of
    // AlreadyExistsException of challengeReport
    @Test
    public void createChallengeReportBadRequestTest3() throws Exception {
        given(this.reportsService.createChallengeReport(any(ReportDTO.class)))
                .willThrow(new AlreadyExistsException("ChallengeReport already exists"));

        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(dto);

        this.mvc.perform(post("/call/createChallengeReport")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    // Test for updateReport with status code 201
    @Test
    public void updateReportTest() throws Exception {
        given(this.reportsService.updateChallengeReport(any(Long.class), any(ReportDTO.class)))
                .willReturn(challengeReport);

        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(dto);

        this.mvc.perform(put("/call/updateChallengeReport/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.challenge.title").value("Challenge 1"))
                .andExpect(jsonPath("$.challenge.description").value("Description 1"))
                .andExpect(jsonPath("$.challenge.user.id").isEmpty())
                .andExpect(jsonPath("$.description").value("Description 1"))
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    // Test for updateReport with status code 404 because of NotFoundException of
    // challengeReport
    @Test
    public void updateReportBadRequestTest() throws Exception {
        given(this.reportsService.updateChallengeReport(any(Long.class), any(ReportDTO.class)))
                .willThrow(new NotFoundException("ChallengeReport not found"));

        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(dto);

        this.mvc.perform(put("/call/updateChallengeReport/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // Test for updateReport with status code 404 because of NotFoundException of
    // challenge
    @Test
    public void updateReportBadRequestTest2() throws Exception {
        given(this.reportsService.updateChallengeReport(any(Long.class), any(ReportDTO.class)))
                .willThrow(new NotFoundException("Challenge not found"));

        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(dto);

        this.mvc.perform(put("/call/updateChallengeReport/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // Test for updateReport with status code 404 because of NotFoundException of
    // user
    @Test
    public void updateReportBadRequestTest3() throws Exception {
        given(this.reportsService.updateChallengeReport(any(Long.class), any(ReportDTO.class)))
                .willThrow(new NotFoundException("User not found"));

        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(dto);

        this.mvc.perform(put("/call/updateChallengeReport/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // Test for deleteReport with status code 200
    @Test
    public void deleteReportTest() throws Exception {
        this.mvc.perform(delete("/call/deleteChallengeReport/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // Test for deleteReport with status code 404 because of NotFoundException of
    // user
    @Test
    public void deleteReportBadRequestTest() throws Exception {
        BDDMockito.willThrow(new NotFoundException("User not found")).given(this.reportsService)
                .deleteChallengeReport(any(Long.class));

        this.mvc.perform(delete("/call/deleteChallengeReport/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // Test for deleteReport with status code 404 because of NotFoundException of
    // challenge
    @Test
    public void deleteReportBadRequestTest2() throws Exception {
        BDDMockito.willThrow(new NotFoundException("Challenge not found")).given(this.reportsService)
                .deleteChallengeReport(any(Long.class));

        this.mvc.perform(delete("/call/deleteChallengeReport/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
