package blossom.reports_service.inbound.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

  private JwtValidator jwtValidator;

  public JwtFilter(JwtValidator jwtValidator) {
    this.jwtValidator = jwtValidator;
  }

  @Override
  public void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      FilterChain filterChain) throws ServletException, IOException {
    String token = jwtValidator.resolveToken(httpServletRequest);
    try {
      // check if jwt is valid
      if (token != null && jwtValidator.isValidJWT(token)) {
        // valid
        Authentication auth = jwtValidator.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    } catch (Exception e) {
      // return forbidden
      SecurityContextHolder.clearContext();
      httpServletResponse.sendError(403);
      return;
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
