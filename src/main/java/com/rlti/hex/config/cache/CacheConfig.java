package com.rlti.hex.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    @Primary
    public CacheManager cacheManager() {
        Map<String, CaffeineCache> cacheMap = new HashMap<>();

        // Cache padrão (pode ser usado para persons, addresses, etc.)
        Caffeine<Object, Object> defaultCacheBuilder = Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterAccess(1, TimeUnit.MINUTES)
                .recordStats();

        // Cache de CEPs (com tempo maior)
        Caffeine<Object, Object> zipCodeCacheBuilder = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .recordStats();

        cacheMap.put("persons", new CaffeineCache("persons", defaultCacheBuilder.build()));
        cacheMap.put("addresses", new CaffeineCache("addresses", defaultCacheBuilder.build()));
        cacheMap.put("contacts", new CaffeineCache("contacts", defaultCacheBuilder.build()));
        cacheMap.put("dependents", new CaffeineCache("dependents", defaultCacheBuilder.build()));
        cacheMap.put("zipCodes", new CaffeineCache("zipCodes", zipCodeCacheBuilder.build()));

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(new ArrayList<>(cacheMap.values()));
        return cacheManager;
    }
}
