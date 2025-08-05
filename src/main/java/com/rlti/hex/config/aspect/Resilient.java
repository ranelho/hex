package com.rlti.hex.config.aspect;

import com.rlti.hex.application.core.domain.constants.ApplicationConstants;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para aplicar padrões de resiliência a métodos.
 * Usa circuit breaker para prevenir falhas em cascata.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Resilient {
    /**
     * Nome do circuit breaker a ser usado.
     */
    String circuitBreaker() default "";

    /**
     * Tempo máximo de execução em milissegundos.
     */
    long timeoutInMs() default ApplicationConstants.Resilience.DEFAULT_TIMEOUT_MS;
}
