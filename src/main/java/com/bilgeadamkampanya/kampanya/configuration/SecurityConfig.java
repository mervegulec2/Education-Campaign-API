package com.bilgeadamkampanya.kampanya.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity, especially for non-browser clients like
                                              // Swagger
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/h2-console/**", // Allow H2 Console access
                                "/api/v1.0/kampanya/**", // Permit all requests to your campaign endpoints
                                "/swagger-ui/**", // Permit access to Swagger UI
                                "/v3/api-docs/**", // Permit access to OpenAPI documentation
                                "/swagger-resources/**", // Permit access to Swagger resources
                                "/webjars/**", // Permit access to webjars (used by Swagger UI)
                                "/v3/api-docs.yaml" // Permit access to OpenAPI spec in YAML format
                        ).permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.disable()) // Disable form login
                .httpBasic(basic -> basic.disable()) // Disable HTTP basic authentication
                .headers(headers -> headers.frameOptions().disable()); // Disable frame options for H2 console

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Using BCryptPasswordEncoder for better security
    }
}
