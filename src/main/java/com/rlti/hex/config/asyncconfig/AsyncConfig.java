package com.rlti.hex.config.asyncconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configuração para operações assíncronas usando Virtual Threads do Java 21
 * para melhorar a eficiência e escalabilidade da aplicação.
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // Usar os Virtual Threads do Java 21 para operações de I/O bound
        executor.setTaskDecorator(runnable -> Thread.ofVirtual().name("hex-virtual-thread").unstarted(runnable));
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("hex-async-");
        executor.initialize();
        return executor;
    }
}
