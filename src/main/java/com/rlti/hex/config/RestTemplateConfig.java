package com.rlti.hex.config;

import com.rlti.hex.application.core.domain.constants.ApplicationConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Configuração do RestTemplate para chamadas de API externas.
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(ApplicationConstants.Connection.DEFAULT_CONNECT_TIMEOUT_MS);
        factory.setReadTimeout(ApplicationConstants.Connection.DEFAULT_READ_TIMEOUT_MS);

        return new RestTemplate(factory);
    }
}
