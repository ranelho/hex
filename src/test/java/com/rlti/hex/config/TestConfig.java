package com.rlti.hex.config;

import com.rlti.hex.application.core.domain.Address;
import com.rlti.hex.application.port.output.ValidateAddressOutputPort;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@TestConfiguration
@Profile("test")
public class TestConfig {

    @Bean
    @Primary
    public ValidateAddressOutputPort mockValidateAddressOutputPort() {
        return new ValidateAddressOutputPort() {
            @Override
            public Address validateAndCompleteAddress(String zipCode) {
                return Address.builder()
                        .street("Rua Teste")
                        .city("Cidade Teste")
                        .state("SP")
                        .neighborhood("Bairro Teste")
                        .zipCode(zipCode)
                        .country("Brasil")
                        .number("123")
                        .build();
            }

            @Override
            public boolean isValidZipCode(String zipCode) {
                return zipCode != null && zipCode.matches("\\d{8}");
            }
        };
    }
}