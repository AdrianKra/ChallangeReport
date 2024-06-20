package blossom.reports_service.inbound.Security;

import jakarta.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import blossom.reports_service.inbound.Security.JwtFilter;
import blossom.reports_service.inbound.Security.JwtValidator;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  private JwtValidator jwtValidator;

  @Autowired
  public SecurityConfig(JwtValidator jwtValidator) {
    this.jwtValidator = jwtValidator;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.cors(cors -> cors.disable())
        .csrf(csrf -> csrf.disable())
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(registry -> registry
            .requestMatchers("/actuator/**").permitAll()
            .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
            .requestMatchers("/swagger-ui.html").permitAll()
            .requestMatchers("/swagger-ui/**").permitAll()
            .requestMatchers("/v3/api-docs/**").permitAll()
            .requestMatchers("/error").permitAll()
            .requestMatchers("/fallback").permitAll()
            .anyRequest().authenticated())
        .addFilterBefore((Filter) new JwtFilter(jwtValidator), UsernamePasswordAuthenticationFilter.class)
        .headers(headers -> headers.frameOptions(header -> header.disable())) // h2-console uses i-frame
        .build();
  }
}