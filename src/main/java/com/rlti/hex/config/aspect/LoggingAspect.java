package com.rlti.hex.config.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("@within(com.rlti.hex.config.aspect.Monitored) ")
    public void multiplePackagesPointcut() {
    }

    @Around("multiplePackagesPointcut()")
    public Object logStart(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String packageName = joinPoint.getTarget().getClass().getPackageName();

        long startTime = System.currentTimeMillis();
        log.info("[startTime] {} - {}", className, methodName);

        try {
            var result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - startTime;
            logExecutionDetails(packageName, className, methodName, elapsedTime, result);
            return result;
        } catch (Throwable e) {
            log.error("Error in method {} in class {}", methodName, className);
            throw e;
        }
    }

    private void logExecutionDetails(String packageName, String className, String methodName, long elapsedTime, Object result) {
        if(packageName.startsWith("com.rlti.hex")) {
           if(result instanceof ResponseEntity){
               int statusCode = ((ResponseEntity<?>) result).getStatusCode().value();
                log.info("[end] {} - {} - {} - {}ms - {}", className, methodName, statusCode, elapsedTime, result);
           }else {
                log.info("[end] {} - {} - {}ms - {}", className, methodName, elapsedTime, result);
           }
        }else {
            log.info("[end] {} - {} - {}ms", className, methodName, elapsedTime);
        }

    }


}

