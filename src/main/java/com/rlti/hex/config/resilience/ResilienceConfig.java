package com.rlti.hex.config.resilience;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Configuração de resiliência para operações críticas.
 * Implementa circuit breaker para prevenir falhas em cascata.
 */
@Configuration
public class ResilienceConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50) // Limite de taxa de falha para abrir o circuit breaker
                .waitDurationInOpenState(Duration.ofSeconds(10)) // Tempo que o circuit breaker permanecerá aberto
                .slidingWindowSize(10) // Número de chamadas para considerar ao calcular a taxa de falha
                .build();

        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public CircuitBreaker personServiceCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker("personService");
    }

    @Bean
    public CircuitBreaker addressServiceCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker("addressService");
    }

    @Bean
    public CircuitBreaker contactServiceCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker("contactService");
    }

    @Bean
    public CircuitBreaker dependentServiceCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker("dependentService");
    }
}
