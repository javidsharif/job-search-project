package com.practice.jobsearchproject.config.security;

import com.practice.jobsearchproject.config.security.jwt.JwtAuthenticationEntryPoint;
import com.practice.jobsearchproject.config.security.jwt.JwtRequestFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtRequestFilter jwtRequestFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults()).csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/companies/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/companies/**").permitAll()
                .antMatchers("/api/v1/companies/**").authenticated()
                .antMatchers("/api/v1/mail/**").authenticated()
                .antMatchers("/api/v1/roles/**").permitAll()
                .antMatchers("/api/v1/auth/**").authenticated()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/v1/candidate/resume").authenticated()
                .antMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/users/**").permitAll()
                .antMatchers("/api/v1/users/**").authenticated()
                .antMatchers(HttpMethod.GET,"/api/v1/vacancies/by/company").authenticated()
                .antMatchers(HttpMethod.POST,"/api/v1/vacancies").authenticated()
                .antMatchers(HttpMethod.PUT,"/api/v1/vacancies").authenticated()
                .antMatchers(HttpMethod.DELETE,"/api/v1/vacancies").authenticated()
                .and()
                .authenticationProvider(authenticationProvider)
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
