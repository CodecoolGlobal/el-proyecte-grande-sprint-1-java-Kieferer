package com.codecool.budapestgo.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.codecool.budapestgo.dao.types.Role.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/",
                                 "/index.html",
                                 "/register",
                                 "/authenticate"
                                ).permitAll()
                .requestMatchers("/client/**",
                        "/pass/**",
                        "/category/all",
                        "/route/all",
                        "/stop/all",
                        "/stops/{name}",
                        "/schedule/all",
                        "/schedule/{name}",
                        "/stops-connected-to-route-id/{routeId}"
                    ).hasAnyRole(ADMIN.name(), CUSTOMER.name(), EMPLOYEE.name())
                .requestMatchers("/client/**",
                                 "/category/**",
                                 "/pass/**",
                                 "/route/**",
                                 "/stop/**",
                                 "/schedule/**"
                                ).hasAnyRole(ADMIN.name(),EMPLOYEE.name())
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
