package com.kaan.kalaha.config;

import com.kaan.kalaha.security.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .headers()
                .frameOptions().disable()
                .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                .and()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/v1/auth/login",
                        "/v1/auth/register",
                        "/h2-console/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/webjars/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic().disable()
                .logout().disable();
    }
}