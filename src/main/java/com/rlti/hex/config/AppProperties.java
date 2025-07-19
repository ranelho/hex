package com.rlti.hex.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configurações centralizadas da aplicação.
 * Propriedades podem ser configuradas via application.yml ou variáveis de ambiente.
 */
@Component
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppProperties {

    /**
     * Configurações de log da aplicação.
     */
    private final Logging logging = new Logging();

    /**
     * Configurações de cache da aplicação.
     */
    private final Cache cache = new Cache();

    /**
     * Configurações de monitoramento da aplicação.
     */
    private final Monitoring monitoring = new Monitoring();

    @Getter
    @Setter
    public static class Logging {
        /**
         * Se deve incluir detalhes de contexto nos logs.
         */
        private boolean includeContextDetails = true;

        /**
         * Tamanho máximo de resposta a ser logada.
         */
        private int maxResponseSizeInLog = 1000;

        /**
         * Se deve mostrar stack traces completos nos logs.
         */
        private boolean includeFullStackTrace = false;
    }

    @Getter
    @Setter
    public static class Cache {
        /**
         * Tempo padrão de expiração do cache em segundos.
         */
        private long defaultTimeToLiveSeconds = 300;

        /**
         * Tamanho máximo do cache.
         */
        private long maxSize = 1000;
    }

    @Getter
    @Setter
    public static class Monitoring {
        /**
         * Limite padrão para execução lenta em milissegundos.
         */
        private long slowExecutionThresholdMs = 1000;

        /**
         * Se deve logar automaticamente métodos lentos.
         */
        private boolean logSlowMethods = true;
    }
}
