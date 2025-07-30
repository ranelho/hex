package com.rlti.hex.config.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rlti.hex.adapters.input.api.response.PageResult;
import com.rlti.hex.config.aspect.Monitored.LogLevel;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Aspecto responsável pelo monitoramento e registro de logs de execução de métodos e classes.
 * Fornece informações detalhadas sobre tempo de execução, parâmetros, resultados e erros.
 */
@Log4j2
@Aspect
@Component
public class LoggingAspect {

    private final ObjectMapper objectMapper;
    private final Map<String, MethodExecutionContext> activeExecutions = new ConcurrentHashMap<>();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public LoggingAspect() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // Configurar para não falhar em referências circulares
        this.objectMapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
    }

    @Pointcut("@within(com.rlti.hex.config.aspect.Monitored) || @annotation(com.rlti.hex.config.aspect.Monitored)")
    public void monitoredPointcut() {
    }

    /**
     * Intercepta a execução de métodos monitorados, registrando logs no início e fim da execução.
     */
    @Around("monitoredPointcut()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // Obter configuração de monitoramento
        Monitored monitoredAnnotation = method.getAnnotation(Monitored.class);
        if (monitoredAnnotation == null) {
            monitoredAnnotation = method.getDeclaringClass().getAnnotation(Monitored.class);
        }

        // Gerar ID único para esta execução
        String executionId = UUID.randomUUID().toString();

        // Criar contexto de execução
        MethodExecutionContext context = new MethodExecutionContext(
                joinPoint.getTarget().getClass().getSimpleName(),
                method.getName(),
                joinPoint.getTarget().getClass().getPackageName(),
                System.currentTimeMillis(),
                monitoredAnnotation,
                Thread.currentThread().getName(),
                LocalDateTime.now().format(dateFormatter)
        );

        activeExecutions.put(executionId, context);

        // Adicionar informações ao MDC para facilitar correlação nos logs
        MDC.put("executionId", executionId);
        MDC.put("className", context.className);
        MDC.put("methodName", context.methodName);

        try {
            // Registrar início da execução
            logExecutionStart(context, joinPoint, monitoredAnnotation);

            // Executar o método
            Object result = joinPoint.proceed();

            // Registrar fim da execução
            long elapsedTime = System.currentTimeMillis() - context.startTime;
            logExecutionEnd(context, elapsedTime, result, monitoredAnnotation);

            return result;
        } catch (Throwable e) {
            // Registrar erro
            long elapsedTime = System.currentTimeMillis() - context.startTime;
            logExecutionError(context, elapsedTime, e);
            throw e;
        } finally {
            activeExecutions.remove(executionId);
            MDC.remove("executionId");
            MDC.remove("className");
            MDC.remove("methodName");
        }
    }

    /**
     * Registra informações no log quando um método monitorado lança uma exceção.
     */
    @AfterThrowing(pointcut = "monitoredPointcut()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.error("Exception em {} - {}: {} - {}", 
                className, 
                methodName, 
                ex.getClass().getSimpleName(), 
                ex.getMessage(), 
                ex);
    }

    /**
     * Registra o início da execução de um método monitorado.
     */
    private void logExecutionStart(MethodExecutionContext context, ProceedingJoinPoint joinPoint, Monitored config) {
        StringBuilder logMessage = new StringBuilder()
                .append("[START] ")
                .append(context.className)
                .append(" - ")
                .append(context.methodName);

        if (config.level() != LogLevel.BASIC) {
            logMessage.append(" - Thread: ")
                      .append(context.threadName)
                      .append(" - Time: ")
                      .append(context.startTimeFormatted);

            if (config.logParameters()) {
                try {
                    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                    String[] parameterNames = signature.getParameterNames();
                    Object[] args = joinPoint.getArgs();

                    Map<String, Object> params = new HashMap<>();
                    for (int i = 0; i < parameterNames.length; i++) {
                        params.put(parameterNames[i], args[i]);
                    }

                    logMessage.append(" - Parameters: ")
                              .append(objectMapper.writeValueAsString(params));
                } catch (Exception e) {
                    logMessage.append(" - Parameters: [Error serializing parameters]");
                }
            }
        }

        log.info(logMessage.toString());
    }

    /**
     * Registra o fim da execução de um método monitorado.
     */
    private void logExecutionEnd(MethodExecutionContext context, long elapsedTime, Object result, Monitored config) {
        boolean isSlowExecution = elapsedTime > config.slowExecutionThresholdMs();

        StringBuilder logMessage = new StringBuilder()
                .append("[END] ")
                .append(context.className)
                .append(" - ")
                .append(context.methodName);

        // Adicionar status code para ResponseEntity
        if (result instanceof ResponseEntity) {
            int statusCode = ((ResponseEntity<?>) result).getStatusCode().value();
            logMessage.append(" - Status: ")
                      .append(statusCode);
        }

        logMessage.append(" - Time: ")
                  .append(elapsedTime)
                  .append("ms");

        if (isSlowExecution) {
            logMessage.append(" [SLOW]");
        }

        if (config.logResult() && result != null) {
            try {
                if (config.level() == LogLevel.DETAILED || config.level() == LogLevel.TRACE) {
                    String resultStr;

                    // Usar toString() para objetos de domínio para evitar problemas de serialização
                    if (result instanceof PageResult) {
                        resultStr = result.toString();
                    } else if (result instanceof ResponseEntity && ((ResponseEntity<?>) result).getBody() != null) {
                        Object body = ((ResponseEntity<?>) result).getBody();
                        // Verificar se o corpo é um objeto de domínio com toString implementado
                        resultStr = getObjectString(body);
                    } else {
                        resultStr = getObjectString(result);
                    }

                    // Limitar tamanho da resposta no log
                    if (resultStr.length() > 1000) {
                        resultStr = resultStr.substring(0, 997) + "...";
                    }

                    logMessage.append(" - Result: ")
                              .append(resultStr);
                }
            } catch (Exception e) {
                logMessage.append(" - Result: [Error serializing result]");
            }
        }

        if (isSlowExecution) {
            log.warn(logMessage.toString());
        } else {
            log.info(logMessage.toString());
        }
    }

    /**
     * Registra informações no log quando um método monitorado termina com erro.
     */
    private void logExecutionError(MethodExecutionContext context, long elapsedTime, Throwable error) {
        log.error("[ERROR] {} - {} - {}ms - Exception: {} - {}", 
                context.className, 
                context.methodName, 
                elapsedTime, 
                error.getClass().getSimpleName(), 
                error.getMessage(),
                error);
    }

    /**
     * Obtém uma representação em string de um objeto, priorizando o método toString()
     * e usando serialização JSON como fallback, com tratamento adequado para evitar
     * problemas com referências circulares.
     */
    private String getObjectString(Object obj) {
        if (obj == null) {
            return "null";
        }

        // Se o objeto for do nosso domínio, usar toString() implementado
        if (obj instanceof com.rlti.hex.application.core.domain.Person ||
            obj instanceof com.rlti.hex.application.core.domain.Address ||
            obj instanceof com.rlti.hex.application.core.domain.Contact ||
            obj instanceof com.rlti.hex.application.core.domain.Dependent ||
            obj instanceof com.rlti.hex.adapters.input.api.response.PageResult) {
            return obj.toString();
        }

        // Para outros objetos, tentar serialização JSON com tratamento de erro
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return obj.getClass().getSimpleName() + "@" + Integer.toHexString(obj.hashCode());
        }
    }

    /**
     * Classe para armazenar o contexto de execução de um método monitorado.
     */
    private static class MethodExecutionContext {
        final String className;
        final String methodName;
        final String packageName;
        final long startTime;
        final Monitored config;
        final String threadName;
        final String startTimeFormatted;

        public MethodExecutionContext(String className, String methodName, String packageName, 
                                      long startTime, Monitored config, String threadName,
                                      String startTimeFormatted) {
            this.className = className;
            this.methodName = methodName;
            this.packageName = packageName;
            this.startTime = startTime;
            this.config = config;
            this.threadName = threadName;
            this.startTimeFormatted = startTimeFormatted;
        }
    }


}

