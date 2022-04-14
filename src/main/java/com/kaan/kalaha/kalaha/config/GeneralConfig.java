package com.kaan.kalaha.kalaha.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * General configuration class
 * 
 * Registers objectMapper bean {@link ObjectMapper}
 * and passwordEncoder bean {@link PasswordEncoder} as type of {@link BCryptPasswordEncoder}
 */
@EnableConfigurationProperties
@Configuration
public class GeneralConfig {
    
    /**
     * ObjectMapper bean
     * 
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    /**
     * PasswordEncoder bean
     * 
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
