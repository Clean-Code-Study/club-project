package com.dbs.club.infrastructure.oauth.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebMvc
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          .sessionManagement(session -> session
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          )
          .authorizeHttpRequests(authz -> authz
              .requestMatchers("/api/oauth/**").permitAll()
          )
          .authorizeHttpRequests(authz -> authz
              .anyRequest().authenticated()
          );
        return http.build();
    }
}
