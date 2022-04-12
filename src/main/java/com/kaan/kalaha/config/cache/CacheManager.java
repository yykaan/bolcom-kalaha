package com.kaan.kalaha.config.cache;

public interface CacheManager {
    void save(String key, String value);

    String getValue(String key);

    Boolean delete(String key);
}
