package com.kaan.kalaha.config;

import com.kaan.kalaha.config.cache.CacheManager;
import com.kaan.kalaha.security.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtFilterConfig {

    @Bean
    @Qualifier("jwtFilter")
    JwtFilter jwtFilter(@Qualifier("inMemoryCacheManager") CacheManager cacheManager) {
        return new JwtFilter(cacheManager);
    }
}