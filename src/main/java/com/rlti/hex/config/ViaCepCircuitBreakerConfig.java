package com.rlti.hex.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Configuração do Circuit Breaker para APIs externas.
 */
@Configuration
public class ViaCepCircuitBreakerConfig {

    private static final String VIA_CEP_CIRCUIT_BREAKER = "viaCepCircuitBreaker";

    @Bean
    public CircuitBreaker viaCepCircuitBreaker() {
        // Cria uma configuração personalizada para o circuit breaker
        CircuitBreakerConfig config =
                CircuitBreakerConfig.custom()
                .slidingWindowType(SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(10)
                .failureRateThreshold(50.0f)  // 50% de falhas para abrir o circuito
                .waitDurationInOpenState(Duration.ofSeconds(30))  // tempo em estado aberto
                .permittedNumberOfCallsInHalfOpenState(5)  // chamadas permitidas em estado semi-aberto
                .minimumNumberOfCalls(5)  // mínimo de chamadas para calcular taxa de falha
                .recordExceptions(Exception.class)
                .build();

        // Cria um registry com a configuração personalizada
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);

        // Retorna o circuit breaker configurado
        return registry.circuitBreaker(VIA_CEP_CIRCUIT_BREAKER);
    }
}
