package com.rlti.hex.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.rlti.hex.application.core.domain.constants.ApplicationConstants;
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

        Caffeine<Object, Object> defaultCacheBuilder = Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterAccess(1, TimeUnit.MINUTES)
                .recordStats();

        Caffeine<Object, Object> zipCodeCacheBuilder = Caffeine.newBuilder()
                .maximumSize(ApplicationConstants.Cache.DEFAULT_MAX_SIZE)
                .expireAfterWrite(ApplicationConstants.Cache.DEFAULT_TTL_SECONDS, TimeUnit.SECONDS)
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
