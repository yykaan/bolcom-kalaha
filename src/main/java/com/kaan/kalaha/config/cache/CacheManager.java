package com.kaan.kalaha.config.cache;

/**
 * Allows developers to make transition between cache implementations.
 */
public interface CacheManager {
    /**
     * Saves key with value to provided cache
     * @param key  key
     * @param value value
     */
    void save(String key, String value);

    /**
     * Returns value by key from provided cache
     * @param key key
     * @return value
     */
    String getValue(String key);

    /**
     * Removes key from provided cache
     * @param key key
     * @return true if key exists in cache
     */
    Boolean delete(String key);
}
