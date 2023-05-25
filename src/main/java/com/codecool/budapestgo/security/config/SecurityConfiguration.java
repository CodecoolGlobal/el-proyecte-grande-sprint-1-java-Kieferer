package com.codecool.budapestgo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.codecool.budapestgo.dao.types.Role.*;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(
                                 "/register",
                                 "/oauth2/**",
                                 "/login/oauth2/**",
                                 "/authenticate",
                                "/",
                                "/frontend/index",
                                "/index.html",
                                "/static/**",
                                "/*.ico",
                                "/*.json",
                                "/*.png",
                                "/frontend/**",
                                "/home"
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
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
    }

    private ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .clientId("65474723555-d7cch1n8uu1c397gkipr0v93b7neeikg.apps.googleusercontent.com")
                .clientSecret("gGOCSPX-E4prNi6gEnwCF-b374iCmT7ovTgz")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .scope("openid", "profile", "email", "address", "phone")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .clientName("Google")
                .build();
    }
}
