package com.rlti.hex.adapters.output.gateway;

import com.rlti.hex.adapters.output.client.ViaCepResponse;
import com.rlti.hex.application.core.domain.Address;
import com.rlti.hex.application.port.output.ValidateAddressOutputPort;
import com.rlti.hex.config.aspect.Monitored;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * Gateway para integração com a API ViaCEP.
 */
@Component
@Monitored
public class ViaCepGateway implements ValidateAddressOutputPort {

    private static final Logger logger = LoggerFactory.getLogger(ViaCepGateway.class);
    private static final String ZIP_CODE_ENDPOINT = "/ws/{zipCode}/json/";

    @Value("${api.viacep.base-url}")
    private String viaCepBaseUrl;

    private final RestTemplate restTemplate;
    private final CircuitBreaker circuitBreaker;

    /**
     * Construtor que recebe as dependências necessárias.
     * 
     * @param restTemplate cliente HTTP para comunicação com APIs externas
     * @param viaCepCircuitBreaker circuit breaker para a API ViaCEP
     */
    public ViaCepGateway(RestTemplate restTemplate, CircuitBreaker viaCepCircuitBreaker) {
        this.restTemplate = restTemplate;
        this.circuitBreaker = viaCepCircuitBreaker;
    }

    @Override
    @Cacheable(value = "zipCodes", cacheManager = "zipCodesCacheManager")
    public Address validateAndCompleteAddress(String zipCode) {
        String formattedZipCode = formatZipCode(zipCode);

        try {
            Supplier<ViaCepResponse> supplier = circuitBreaker.decorateSupplier(() -> {
                String url = viaCepBaseUrl + ZIP_CODE_ENDPOINT.replace("{zipCode}", formattedZipCode);
                return restTemplate.getForObject(url, ViaCepResponse.class);
            });

            ViaCepResponse response = supplier.get();

            if (response != null && response.isValid()) {
                return mapToAddress(response);
            } else {
                logger.warn("CEP inválido ou não encontrado: {}", formattedZipCode);
                return createEmptyAddress(formattedZipCode);
            }

        } catch (CallNotPermittedException e) {
            logger.warn("Circuit breaker aberto para ViaCEP: {}", e.getMessage());
            return createEmptyAddress(formattedZipCode);
        } catch (RestClientException e) {
            logger.error("Erro ao consultar ViaCEP: {}", e.getMessage());
            return createEmptyAddress(formattedZipCode);
        } catch (Exception e) {
            logger.error("Erro inesperado ao consultar ViaCEP: {}", e.getMessage());
            return createEmptyAddress(formattedZipCode);
        }
    }

    @Override
    public boolean isValidZipCode(String zipCode) {
        String formattedZipCode = formatZipCode(zipCode);

        try {
            // Definir a lógica de validação como um BooleanSupplier
            BooleanSupplier validationLogic = () -> {
                String url = viaCepBaseUrl + ZIP_CODE_ENDPOINT.replace("{zipCode}", formattedZipCode);
                ViaCepResponse response = restTemplate.getForObject(url, ViaCepResponse.class);
                return response != null && response.isValid();
            };

            // Adaptar o BooleanSupplier para uso com o circuit breaker 
            // e converter o resultado de volta para um BooleanSupplier em uma única expressão
            return circuitBreaker.decorateSupplier(validationLogic::getAsBoolean).get();

        } catch (Exception e) {
            logger.warn("Erro ao validar CEP: {}", e.getMessage());
            return true; // Considera válido em caso de erro para não bloquear o fluxo
        }
    }

    private Address mapToAddress(ViaCepResponse response) {
        Address address = new Address();
        address.setZipCode(response.getZipCode());
        address.setStreet(response.getStreet());
        address.setNeighborhood(response.getNeighborhood());
        address.setCity(response.getCity());
        address.setState(response.getState());
        address.setCountry("Brasil");
        return address;
    }

    private Address createEmptyAddress(String zipCode) {
        Address address = new Address();
        address.setZipCode(zipCode);
        return address;
    }

    private String formatZipCode(String zipCode) {
        if (zipCode == null) {
            return "";
        }
        // Remove caracteres não numéricos
        return zipCode.replaceAll("\\D", "");
    }
}
