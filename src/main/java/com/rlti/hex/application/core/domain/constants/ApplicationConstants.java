package com.rlti.hex.application.core.domain.constants;

/**
 * Constantes da aplicação para evitar valores hardcoded no código.
 * Centraliza valores numéricos e strings utilizados em múltiplos locais.
 * 
 * @author Sistema
 * @since 1.0
 */
public final class ApplicationConstants {

    private ApplicationConstants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Constantes relacionadas a telefones e contatos.
     */
    public static final class Phone {
        public static final String TOLL_FREE_PREFIX_0800 = "0800";
        public static final String TOLL_FREE_PREFIX_4004 = "4004";
        public static final String TOLL_FREE_PREFIX_3003 = "3003";
        public static final String TOLL_FREE_PREFIX_3004 = "3004";
        public static final int MOBILE_PHONE_LENGTH = 9;
        public static final int LANDLINE_PHONE_LENGTH = 8;
        public static final String PHONE_SEPARATOR = "-";
        
        private Phone() {
            throw new UnsupportedOperationException("Utility class cannot be instantiated");
        }
    }

    /**
     * Constantes relacionadas a cache.
     */
    public static final class Cache {
        public static final long DEFAULT_MAX_SIZE = 1000L;
        public static final int DEFAULT_TTL_SECONDS = 300;
        
        private Cache() {
            throw new UnsupportedOperationException("Utility class cannot be instantiated");
        }
    }

    /**
     * Constantes relacionadas a monitoramento e logging.
     */
    public static final class Monitoring {
        public static final long DEFAULT_SLOW_EXECUTION_THRESHOLD_MS = 1000L;
        public static final int DEFAULT_MAX_RESPONSE_SIZE_IN_LOG = 1000;
        
        private Monitoring() {
            throw new UnsupportedOperationException("Utility class cannot be instantiated");
        }
    }

    /**
     * Constantes relacionadas a circuit breaker e resiliência.
     */
    public static final class Resilience {
        public static final int DEFAULT_SLIDING_WINDOW_SIZE = 10;
        public static final int DEFAULT_FAILURE_RATE_THRESHOLD = 50;
        public static final long DEFAULT_WAIT_DURATION_SECONDS = 10L;
        public static final int DEFAULT_PERMITTED_CALLS_HALF_OPEN = 5;
        public static final long DEFAULT_TIMEOUT_MS = 1000L;
        
        private Resilience() {
            throw new UnsupportedOperationException("Utility class cannot be instantiated");
        }
    }

    /**
     * Constantes relacionadas a configurações de thread pool.
     */
    public static final class ThreadPool {
        public static final int DEFAULT_CORE_POOL_SIZE = 10;
        public static final int DEFAULT_MAX_POOL_SIZE = 50;
        
        private ThreadPool() {
            throw new UnsupportedOperationException("Utility class cannot be instantiated");
        }
    }

    /**
     * Constantes relacionadas a timeouts de conexão.
     */
    public static final class Connection {
        public static final int DEFAULT_CONNECT_TIMEOUT_MS = 5000;
        public static final int DEFAULT_READ_TIMEOUT_MS = 5000;
        
        private Connection() {
            throw new UnsupportedOperationException("Utility class cannot be instantiated");
        }
    }

    /**
     * Constantes relacionadas a dashboard e estatísticas.
     */
    public static final class Dashboard {
        public static final int DEFAULT_RECENT_DAYS = 30;
        public static final int DEFAULT_RECENT_LIMIT = 10;
        
        private Dashboard() {
            throw new UnsupportedOperationException("Utility class cannot be instantiated");
        }
    }

    /**
     * Constantes relacionadas a URLs e endpoints.
     */
    public static final class Urls {
        public static final String LOCALHOST_FRONTEND = "http://localhost:3000";
        public static final String LOCALHOST_BACKEND = "http://localhost:8080";
        
        private Urls() {
            throw new UnsupportedOperationException("Utility class cannot be instantiated");
        }
    }
}