package com.rlti.hex.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Configuração dos caches da aplicação.
 * Utiliza Caffeine como provedor de cache.
 * 
 * Esta é a implementação principal de cache para a aplicação.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    @Primary
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        // Configura o cache padrão
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .recordStats());

        // Adiciona os nomes dos caches que serão gerenciados
        cacheManager.setCacheNames(Arrays.asList("zipCodes", "persons", "addresses", "contacts", "dependents"));

        return cacheManager;
    }

    /**
     * Cache específico para CEPs com TTL mais longo, já que CEPs raramente mudam
     */
    @Bean
    public CacheManager zipCodesCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("zipCodes");

        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(24, TimeUnit.HOURS)  // CEPs raramente mudam, podemos cachear por mais tempo
                .recordStats());

        return cacheManager;
    }
}
