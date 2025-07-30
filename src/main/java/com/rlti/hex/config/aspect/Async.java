package com.rlti.hex.config.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para indicar que um método deve ser executado de forma assíncrona.
 * Usa os Virtual Threads do Java 21 para melhorar a performance de operações de I/O.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Async {
    /**
     * Nome do executor a ser utilizado.
     */
    String executor() default "taskExecutor";
}
