package com.rlti.hex.config.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Aspecto que gerencia a execução assíncrona para métodos anotados com @Async.
 * Utiliza os Virtual Threads do Java 21 para melhorar a performance.
 */
@Aspect
@Component
public class AsyncAspect {

    private static final Logger logger = LoggerFactory.getLogger(AsyncAspect.class);
    private final ApplicationContext applicationContext;

    public AsyncAspect(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Around("@annotation(com.rlti.hex.config.aspect.Async)")
    public Object executeAsync(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Async asyncAnnotation = method.getAnnotation(Async.class);
        String executorName = asyncAnnotation.executor();

        Executor executor = applicationContext.getBean(executorName, Executor.class);

        // Verifica se o método retorna CompletableFuture
        boolean returnsCompletableFuture = CompletableFuture.class.isAssignableFrom(method.getReturnType());

        if (returnsCompletableFuture) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return joinPoint.proceed();
                } catch (Throwable e) {
                    logger.error("Error executing async method {}", method.getName(), e);
                    throw new RuntimeException(e);
                }
            }, executor);
        } else {
            // Se não retorna CompletableFuture, executa de forma assíncrona e retorna null
            executor.execute(() -> {
                try {
                    joinPoint.proceed();
                } catch (Throwable e) {
                    logger.error("Error executing async method {}", method.getName(), e);
                }
            });
            return null;
        }
    }
}
