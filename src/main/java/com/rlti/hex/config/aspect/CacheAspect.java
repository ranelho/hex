package com.rlti.hex.config.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Aspecto que gerencia o cache para métodos anotados com @Cached.
 */
@Aspect
@Component
public class CacheAspect {

    private static final Logger logger = LoggerFactory.getLogger(CacheAspect.class);
    private final CacheManager cacheManager;

    public CacheAspect(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Around("@annotation(com.rlti.hex.config.aspect.Cached) || @within(com.rlti.hex.config.aspect.Cached)")
    public Object cacheable(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Cached cachedAnnotation = method.getAnnotation(Cached.class);
        if (cachedAnnotation == null) {
            cachedAnnotation = method.getDeclaringClass().getAnnotation(Cached.class);
        }

        String cacheName = cachedAnnotation.cacheName().isEmpty() ? 
                method.getDeclaringClass().getSimpleName() : cachedAnnotation.cacheName();

        String cacheKey = generateCacheKey(method, joinPoint.getArgs());

        var cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            var cachedValue = cache.get(cacheKey);
            if (cachedValue != null) {
                logger.debug("Cache hit for method {} with key {}", method.getName(), cacheKey);
                return cachedValue.get();
            }
        }

        // Cache miss, execute method and store result
        Object result = joinPoint.proceed();
        if (cache != null && result != null) {
            logger.debug("Caching result of method {} with key {}", method.getName(), cacheKey);
            cache.put(cacheKey, result);
        }

        return result;
    }

    private String generateCacheKey(Method method, Object[] args) {
        return method.getName() + ":" + Arrays.deepToString(args);
    }
}
