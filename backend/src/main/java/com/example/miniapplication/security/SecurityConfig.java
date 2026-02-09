package com.example.miniapplication.security;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtService jwtService) throws Exception {

    AuthenticationEntryPoint entryPoint = (request, response, authException) -> {
      response.setStatus(401);
      response.setContentType("application/json");
      response.getWriter().write("{\"message\":\"Unauthorized\"}");
    };

    AccessDeniedHandler deniedHandler = (request, response, accessDeniedException) -> {
      response.setStatus(403);
      response.setContentType("application/json");
      response.getWriter().write("{\"message\":\"Forbidden\"}");
    };

    http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(req -> {
          CorsConfiguration cfg = new CorsConfiguration();
          cfg.setAllowedOrigins(List.of("http://localhost:3000"));
          cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
          cfg.setAllowedHeaders(List.of("*"));
          cfg.setAllowCredentials(false);
          return cfg;
        }))
        .exceptionHandling(ex -> ex
            .authenticationEntryPoint(entryPoint)
            .accessDeniedHandler(deniedHandler))
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
            .anyRequest().authenticated());

    http.addFilterBefore(new JwtAuthFilter(jwtService), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
