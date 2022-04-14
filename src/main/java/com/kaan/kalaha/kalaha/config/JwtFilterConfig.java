package com.kaan.kalaha.kalaha.config;

import com.kaan.kalaha.config.cache.CacheManager;
import com.kaan.kalaha.security.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Creates JwtFilter bean by provided CacheManager implementation {@link CacheManager}
 *
 * For simplicity, it uses {@link com.kaan.kalaha.config.cache.InMemoryCacheManager}
 */
@Configuration
public class JwtFilterConfig {

    /**
     * @param cacheManager {@link CacheManager}
     * @return {@link JwtFilter} bean with {@link com.kaan.kalaha.config.cache.InMemoryCacheManager}
     */
    @Bean
    @Qualifier("jwtFilter")
    JwtFilter jwtFilter(@Qualifier("inMemoryCacheManager") CacheManager cacheManager) {
        return new JwtFilter(cacheManager);
    }
}
