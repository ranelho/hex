package com.rlti.hex.config.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para monitorar métodos e classes.
 * Quando aplicada, registra logs detalhados da execução incluindo tempo de processamento,
 * parâmetros de entrada e resultado.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Monitored {
    /**
     * Nível de detalhamento do log.
     */
    LogLevel level() default LogLevel.BASIC;

    /**
     * Se deve ou não registrar os parâmetros de entrada no log.
     */
    boolean logParameters() default false;

    /**
     * Se deve ou não registrar o resultado no log.
     */
    boolean logResult() default true;

    /**
     * Limite de tempo em milissegundos para considerar a execução como lenta.
     * Quando a execução ultrapassar este tempo, será logado como WARNING.
     */
    long slowExecutionThresholdMs() default 1000;

    /**
     * Níveis de detalhamento disponíveis para o log.
     */
    enum LogLevel {
        /**
         * Apenas informações básicas: classe, método, tempo de execução.
         */
        BASIC,

        /**
         * Informações detalhadas incluindo parâmetros e resultado.
         */
        DETAILED,

        /**
         * Informações completas incluindo contexto de execução, thread, etc.
         */
        TRACE
    }
}
