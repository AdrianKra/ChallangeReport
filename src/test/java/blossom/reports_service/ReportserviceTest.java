package blossom.reports_service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.RestTemplate;

import blossom.reports_service.inbound.DTOs.ChallengeDTO;
import blossom.reports_service.model.Entities.*;
import blossom.reports_service.model.Enums.Visibility;
import blossom.reports_service.model.Exceptions.NotFoundException;
import blossom.reports_service.model.Exceptions.UnauthorizedException;
import blossom.reports_service.model.Repositories.*;
import blossom.reports_service.model.Services.ReportsService;

@ExtendWith(MockitoExtension.class)
class ReportsServiceTest {

  @Mock
  private ChallengeReportRepository challengeReportRepository;

  @Mock
  private ChallengeSummaryRepository challengeSummaryRepository;

  @Mock
  private SecurityContext securityContext;

  @Mock
  private Authentication authentication;

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private UserDetails userDetails;

  @InjectMocks
  private ReportsService reportsService;

  private User user;
  private ChallengeDTO challengeDTO;
  private Challenge challenge;
  private ChallengeReport challengeReport;
  private ChallengeSummary challengeSummary;
  private ChallengeProgress challengeProgress;

  public final String email = "test@example.com";
  public final Long id = 1L;
  public final Date date = new Date();
  public final Double progress = 50.0;
  public final Visibility visibility = Visibility.FRIENDS;

  private static final String USER_SERVICE_URL = "http://localhost:8080/rest/users";
  private static final String CHALLENGE_SERVICE_URL = "http://localhost:8081/rest/challenge";

  @BeforeEach
  void setUp() {
    user = new User();
    user.setEmail(email);

    challengeDTO = mock(ChallengeDTO.class);
    challengeDTO.setId(id);
    challengeDTO.setDeadline(date);

    challenge = mock(Challenge.class);
    challenge.setId(id);
    challenge.setDeadline(date);

    challengeReport = mock(ChallengeReport.class);
    challengeSummary = mock(ChallengeSummary.class);
    challengeProgress = mock(ChallengeProgress.class);

    SecurityContextHolder.setContext(securityContext);
  }

  @Test
  void testCreateChallengeSummary() {
    when(restTemplate.getForObject(any(String.class), eq(User.class))).thenReturn(user);
    when(challengeSummaryRepository.save(any(ChallengeSummary.class))).thenReturn(challengeSummary);

    reportsService.createChallengeSummary(email);

    verify(challengeSummaryRepository, times(1)).save(any(ChallengeSummary.class));
  }

  @Test
  void testGetChallengeSummary() {
    when(restTemplate.getForObject(any(String.class), eq(User.class))).thenReturn(user);
    when(challengeSummaryRepository.findByUser(any(User.class))).thenReturn(Optional.of(challengeSummary));

    ChallengeSummary summary = reportsService.getChallengeSummary(email);

    assertNotNull(summary);
    verify(challengeSummaryRepository, times(1)).findByUser(any(User.class));
  }

  @Test
  void testUpdateChallengeProgress() {
    when(restTemplate.getForObject(any(String.class), eq(User.class))).thenReturn(user);
    when(restTemplate.getForObject(any(String.class), eq(ChallengeDTO.class))).thenReturn(challengeDTO);
    when(challengeReportRepository.findByChallenge(any(Challenge.class))).thenReturn(Optional.of(challengeReport));

    reportsService.updateChallengeProgress(id, id, email, progress, new Date());

    verify(challengeReportRepository, times(1)).findByChallenge(any(Challenge.class));
  }

  @Test
  void testCreateChallengeReport() {
    when(restTemplate.getForObject(any(String.class), eq(User.class))).thenReturn(user);
    when(restTemplate.getForObject(any(String.class), eq(ChallengeDTO.class))).thenReturn(challengeDTO);
    when(challengeSummaryRepository.findByUser(any(User.class))).thenReturn(Optional.of(challengeSummary));
    when(challengeReportRepository.save(any(ChallengeReport.class))).thenReturn(challengeReport);
    when(challengeSummaryRepository.save(any(ChallengeSummary.class))).thenReturn(challengeSummary);

    ChallengeReport report = reportsService.createChallengeReport(email, id);

    assertNotNull(report);
    verify(challengeReportRepository, times(1)).save(any(ChallengeReport.class));
    verify(challengeSummaryRepository, times(1)).save(any(ChallengeSummary.class));
  }

  // @Test
  // void testDeleteChallengeReport() {
  // Date deadline = new Date();

  // // Arrange
  // given(challengeReportRepository.findById(id)).willReturn(Optional.of(challengeReport));
  // given(challengeSummaryRepository.findByUser(user)).willReturn(Optional.of(challengeSummary));
  // given(restTemplate.getForObject(USER_SERVICE_URL + "/getUserByEmail/" +
  // email, User.class)).willReturn(user);
  // given(restTemplate.getForObject(CHALLENGE_SERVICE_URL + "/" + id,
  // ChallengeDTO.class)).willReturn(challengeDTO);
  // given(challengeReportRepository.save(any(ChallengeReport.class))).willReturn(challengeReport);
  // given(challengeSummaryRepository.save(any(ChallengeSummary.class))).willReturn(challengeSummary);
  // given(challenge.getDeadline()).willReturn(deadline);

  // ModelMapper modelMapper = mock(ModelMapper.class);
  // given(modelMapper.map(challengeDTO, Challenge.class)).willReturn(challenge);

  // // Act
  // ChallengeReport updatedReport = reportsService.updateReportStatus(id, id,
  // email, progress, deadline);

  // // Assert
  // verify(challengeReportRepository).findById(id);
  // verify(challengeSummaryRepository).findByUser(user);
  // verify(restTemplate).getForObject(USER_SERVICE_URL + "/getUserByEmail/" +
  // email, User.class);
  // verify(restTemplate).getForObject(CHALLENGE_SERVICE_URL + "/" + id,
  // ChallengeDTO.class);
  // verify(challengeReportRepository).save(challengeReport);
  // verify(challengeSummaryRepository).save(challengeSummary);

  // // Add assertions to check the updated state of challengeReport and
  // // challengeSummary
  // assertNotNull(updatedReport);
  // assertEquals(ChallengeStatus.DONE, updatedReport.getStatus());
  // assertNotNull(challengeSummary.getLastActive());
  // }

  @Test
  void testGetChallengeReports() {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(userDetails);
    when(userDetails.getUsername()).thenReturn("authenticated@example.com");
    when(restTemplate.getForObject(any(String.class), eq(Boolean.class))).thenReturn(true);
    when(restTemplate.getForObject(any(String.class), eq(User.class))).thenReturn(user);
    when(challengeReportRepository.findAllByUser(any(User.class))).thenReturn(List.of(challengeReport));

    Iterable<ChallengeReport> reports = reportsService.getChallengeReports("test@example.com");

    assertNotNull(reports);
    verify(challengeReportRepository, times(1)).findAllByUser(any(User.class));
  }

  @Test
  void testGetChallengeReports_Unauthorized() {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(userDetails);
    when(userDetails.getUsername()).thenReturn("authenticated@example.com");
    when(restTemplate.getForObject(any(String.class), eq(Boolean.class))).thenReturn(false);

    assertThrows(UnauthorizedException.class, () -> reportsService.getChallengeReports("test@example.com"));
  }

  // @Test
  // void testUpdateReportStatus() {
  // Date date = new Date();

  // when(challengeReportRepository.findById(any(Long.class))).thenReturn(Optional.of(challengeReport));
  // when(restTemplate.getForObject(any(String.class),
  // eq(User.class))).thenReturn(user);
  // when(restTemplate.getForObject(any(String.class),
  // eq(ChallengeDTO.class))).thenReturn(challengeDTO);
  // when(challengeSummaryRepository.findByUser(any(User.class))).thenReturn(Optional.of(challengeSummary));
  // when(challengeReportRepository.save(any(ChallengeReport.class))).thenReturn(challengeReport);
  // when(challengeSummaryRepository.save(any(ChallengeSummary.class))).thenReturn(challengeSummary);
  // when(challenge.getDeadline()).thenReturn(date);

  // ChallengeReport updatedReport = reportsService.updateReportStatus(1L, 1L,
  // "test@example.com", 50.0,
  // new Date());

  // assertNotNull(updatedReport);
  // verify(challengeReportRepository, times(1)).findById(any(Long.class));
  // verify(challengeReportRepository, times(1)).save(any(ChallengeReport.class));
  // verify(challengeSummaryRepository,
  // times(1)).save(any(ChallengeSummary.class));
  // }

  @Test
  void testUpdateReportStatus_NotFound() {
    when(challengeReportRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class,
        () -> reportsService.updateReportStatus(1L, 1L, "test@example.com", 50.0, new Date()));
  }

  // Add more tests as needed for the remaining methods
}
