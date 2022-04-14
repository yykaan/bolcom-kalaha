package com.kaan.kalaha.kalaha.config.cache;

import com.kaan.kalaha.config.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache manager {@link CacheManager} implementation for in-memory cache.
 * uses {@link ConcurrentHashMap} as cache.
 */
@Service
public class InMemoryCacheManager implements CacheManager {

    /**
     * Cache map for storing cache entries.
     * used by {@link com.kaan.kalaha.service.AuthService}
     */
    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

    @Override
    public void save(String key, String token) {
        map.put(key, token);
    }

    @Override
    public String getValue(String key) {
        return map.get(key);
    }

    @Override
    public Boolean delete(String key) {
        if (getValue(key) != null) {
            map.remove(key);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
