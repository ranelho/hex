package com.rlti.hex.config.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
public class LoggingAspect {


    @Pointcut("@within(com.rlti.hex.config.aspect.Monitored) ")
    public void multiplePackagesPointcut() {}

    @Before("multiplePackagesPointcut()")
    public void logStart(JoinPoint joinPoint) {
        log.info("Iniciando execução: {}", joinPoint.getSignature().toShortString());
        for (Object arg : joinPoint.getArgs()) {
            log.debug("Argumento: {}", arg);
        }
    }

    @AfterReturning(pointcut = "multiplePackagesPointcut()", returning = "result")
    public void logFinish(JoinPoint joinPoint, Object result) {
        log.info("Execução concluída com sucesso: {}", joinPoint.getSignature().toShortString());
        log.debug("Resultado: {}", result);
    }

}

