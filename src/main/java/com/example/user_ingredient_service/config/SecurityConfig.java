package com.example.user_ingredient_service.config;

import com.example.user_ingredient_service.filter.JwtFilter;
import com.example.user_ingredient_service.repository.UserRoleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@DependsOn("liquibase")
public class SecurityConfig  {

    private final JwtFilter filter;
    private final UserRoleRepository userRoleRepository;
    private String userAuthority;
    private String adminAuthority;

    @PostConstruct
    void setUserAuthorities() {
        userAuthority = userRoleRepository.getUserRoleUser().getAuthority();
        adminAuthority = userRoleRepository.getUserRoleAdmin().getAuthority();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http = http.cors().and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> response.sendError(
                                HttpServletResponse.SC_UNAUTHORIZED,
                                ex.getMessage()
                        )
                )
                .and();

        http.authorizeHttpRequests()
                .requestMatchers(
                        "/api/v1/user/registration",
                        "/api/v1/user/login",
                        "/api/v1/user/refresh_token")
                .anonymous()
                .requestMatchers("/api/v1/user/**")
                .hasAnyAuthority(userAuthority,adminAuthority)
                .requestMatchers("/api/v1/ingredient/**")
                .hasAuthority(adminAuthority)
                .requestMatchers("/api/v1/action/**")
                .hasAnyAuthority(userAuthority,adminAuthority)
                .requestMatchers("/api/v1/user_ingredient/**")
                .hasAnyAuthority(userAuthority,adminAuthority);

        http.addFilterBefore(
                filter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}