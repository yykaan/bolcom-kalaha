package com.kaan.kalaha.config.cache;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryCacheManager implements CacheManager {

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

    @Override
    public Boolean isExist(String key) {
        return map.containsKey(key);
    }
}
