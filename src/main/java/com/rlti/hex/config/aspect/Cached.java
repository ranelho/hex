package com.rlti.hex.config.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para indicar que um método deve ter seu resultado armazenado em cache.
 * Utilizada com Spring Cache para melhorar a performance de operações de leitura frequentes.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cached {
    /**
     * Nome do cache a ser utilizado.
     */
    String cacheName() default "";

    /**
     * Tempo de expiração em segundos.
     */
    long timeToLive() default 300; // 5 minutos por padrão
}
