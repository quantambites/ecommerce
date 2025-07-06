package com.ecom_server.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable for simplicity in development
                .cors(Customizer.withDefaults()) // enable CORS with WebMvcConfigurer
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()
                        //.requestMatchers("/api/common/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
