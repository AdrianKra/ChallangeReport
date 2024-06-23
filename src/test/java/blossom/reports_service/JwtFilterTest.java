package blossom.reports_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.support.ReflectionSupport;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import blossom.reports_service.inbound.Security.JwtFilter;
import blossom.reports_service.inbound.Security.JwtValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtFilterTest {

  @Mock
  private JwtValidator jwtValidator;

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Mock
  private FilterChain filterChain;

  @Mock
  private Authentication authentication;

  @InjectMocks
  private JwtFilter jwtFilter;

  @BeforeEach
  public void setUp() {
    SecurityContextHolder.clearContext();
  }

  @Test
  public void testDoFilterInternal_ValidToken() throws ServletException,
      IOException {
    // Arrange
    String token = "valid-token";
    when(jwtValidator.resolveToken(request)).thenReturn(token);
    when(jwtValidator.isValidJWT(token)).thenReturn(true);
    when(jwtValidator.getAuthentication(token)).thenReturn(authentication);

    // Act
    jwtFilter.doFilterInternal(request, response, filterChain);

    // Assert
    assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    verify(jwtValidator, times(1)).resolveToken(request);
    verify(jwtValidator, times(1)).isValidJWT(token);
    verify(jwtValidator, times(1)).getAuthentication(token);
    verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  public void testDoFilterInternal_InvalidToken() throws ServletException,
      IOException {
    // Arrange
    String token = "invalid-token";
    when(jwtValidator.resolveToken(request)).thenReturn(token);
    when(jwtValidator.isValidJWT(token)).thenReturn(false);

    // Act
    jwtFilter.doFilterInternal(request, response, filterChain);

    // Assert
    assertNull(SecurityContextHolder.getContext().getAuthentication());
    verify(jwtValidator, times(1)).resolveToken(request);
    verify(jwtValidator, times(1)).isValidJWT(token);
    verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  public void testDoFilterInternal_NoToken() throws ServletException,
      IOException {
    // Arrange
    when(jwtValidator.resolveToken(request)).thenReturn(null);

    // Act
    jwtFilter.doFilterInternal(request, response, filterChain);

    // Assert
    assertNull(SecurityContextHolder.getContext().getAuthentication());
    verify(jwtValidator, times(1)).resolveToken(request);
    verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  public void testDoFilterInternal_ExceptionHandling() throws ServletException,
      IOException {
    // Arrange
    String token = "valid-token";
    when(jwtValidator.resolveToken(request)).thenReturn(token);
    when(jwtValidator.isValidJWT(token)).thenThrow(new RuntimeException("JWT validation error"));

    // Act
    jwtFilter.doFilterInternal(request, response, filterChain);

    // Assert
    assertNull(SecurityContextHolder.getContext().getAuthentication());
    verify(jwtValidator, times(1)).resolveToken(request);
    verify(jwtValidator, times(1)).isValidJWT(token);
    verify(response, times(1)).sendError(HttpServletResponse.SC_FORBIDDEN);
    verify(filterChain, never()).doFilter(request, response);
  }
}
