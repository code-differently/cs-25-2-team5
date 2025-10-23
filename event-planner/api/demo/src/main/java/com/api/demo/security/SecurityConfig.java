package com.api.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // private final ClerkAuthFilter clerkAuthFilter;
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    http.authorizeHttpRequests(
        requests-> requests.requestMatchers("api/v1/events/community","/api/v1/users/").permitAll().anyRequest().authenticated()
        );
    return http.build();

  }

}
