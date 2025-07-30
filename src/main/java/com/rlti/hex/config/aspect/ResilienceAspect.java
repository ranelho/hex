package com.rlti.hex.config.aspect;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

/**
 * Aspecto que aplica padrões de resiliência para métodos anotados com @Resilient.
 */
@Aspect
@Component
public class ResilienceAspect {

    private static final Logger logger = LoggerFactory.getLogger(ResilienceAspect.class);
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public ResilienceAspect(CircuitBreakerRegistry circuitBreakerRegistry) {
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    @Around("@annotation(com.rlti.hex.config.aspect.Resilient) || @within(com.rlti.hex.config.aspect.Resilient)")
    public Object applyResilience(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Resilient resilientAnnotation = method.getAnnotation(Resilient.class);
        if (resilientAnnotation == null) {
            resilientAnnotation = method.getDeclaringClass().getAnnotation(Resilient.class);
        }

        String circuitBreakerName = resilientAnnotation.circuitBreaker().isEmpty() ? 
                method.getDeclaringClass().getSimpleName() : resilientAnnotation.circuitBreaker();

        long timeout = resilientAnnotation.timeoutInMs();

        // Obter ou criar circuit breaker
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(circuitBreakerName);

        // Decorar a função com circuit breaker
        Supplier<Object> decoratedSupplier = CircuitBreaker.decorateSupplier(circuitBreaker, () -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });

        try {
            // Executar com timeout
            CompletableFuture<Object> future = CompletableFuture.supplyAsync(decoratedSupplier);
            return future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            logger.error("Method {} timed out after {} ms", method.getName(), timeout);
            throw new RuntimeException("Operation timed out", e);
        } catch (Exception e) {
            logger.error("Error executing method {} with circuit breaker", method.getName(), e);
            throw e.getCause() instanceof Exception ? (Exception) e.getCause() : e;
        }
    }
}
