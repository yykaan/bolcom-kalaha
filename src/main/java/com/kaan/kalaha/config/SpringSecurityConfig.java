package com.kaan.kalaha.config;

import com.kaan.kalaha.security.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * Configures Spring Security with given configuration.
 * uses {@link JwtFilter} to filter requests whether they are authorized or not.
 */
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;

    /**
     * @param http Security configuration
     * <p>csrf is disabled because we are using tokens</p>
     *
     * <p>cors is enabled to allow cross origin requests</p>
     *
     * <p>add jwtFilter {@link JwtFilter} as add {@link UsernamePasswordAuthenticationFilter} to filter requests whether they are authorized or not.</p>
     * <p>add headers to allow cross origin requests</p>
     *
     * <p>frame options is disabled to allow iframes in the same domain for H2-Console access</p>
     *
     * <p>session creation is disabled to allow stateless requests with JWT tokens</p>
     *
     * <p>Authorized requests are allowed to access all resources</p>
     *            <p>All has access to; </p>
     *            <p> /actuator/**</p>
     *            <p> /api/v1/auth/login</p>
     *            <p> /api/v1/auth/register</p>
     *            <p> /h2-console/**</p>
     *            <p> /swagger-ui.html</p>
     *            <p> /v3/api-docs</p>
     *            <p> /swagger-resources/**</p>
     *            <p> /swagger-ui.html</p>
     * other requests are filtered by {@link JwtFilter}
     * <p>httbasic is disabled, only JWT is used</p>
     * <p>logout is disabled, only JWT is used so JWT expiration is used to handle logout</p>
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .cors()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .headers()
                .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                .and()
                .frameOptions().sameOrigin()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/actuator/**",
                        "/api/v1/auth/login",
                        "/api/v1/auth/register",
                        "/h2-console/**"
                ).permitAll()
                .antMatchers("/swagger-ui/**", "/v3/**", "/swagger-resources/**", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic().disable()
                .logout().disable();
    }

    /**
     * @return {@link CorsConfiguration} with allowed origins
     * <p>allowed origin is <a href="http://localhost:4200">http://localhost:4200</a> for development purposes</p>
     */
    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Accept",
                "Cache-Control",
                "Content-Type",
                "Origin",
                "ajax",
                "x-csrf-token",
                "x-requested-with"
        ));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
