package com.rlti.hex.config.asyncconfig;

import com.rlti.hex.application.core.domain.constants.ApplicationConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setTaskDecorator(runnable -> Thread.ofVirtual().name("hex-virtual-thread").unstarted(runnable));
        executor.setCorePoolSize(ApplicationConstants.ThreadPool.DEFAULT_CORE_POOL_SIZE);
        executor.setMaxPoolSize(ApplicationConstants.ThreadPool.DEFAULT_MAX_POOL_SIZE);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("hex-async-");
        executor.initialize();
        return executor;
    }
}
