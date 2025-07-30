package com.rlti.hex.adapters.input.api.request;

import com.rlti.hex.application.core.domain.Address;
import com.rlti.hex.application.core.domain.AddressData;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressRequest(
        Long id,
        String street,
        @NotBlank(message = "City is mandatory")
        String city,
        String state,
        String neighborhood,
        @NotBlank(message = "Zip code is mandatory")
        @Size(min = 6, max = 8, message = "Zip code must be 8 characters")
        String zipCode,
        String country,
        String number
) {
    public Address toDomain() {
        // Criar AddressData para encapsular todos os dados de endereço
        AddressData data = new AddressData(
            street, city, state, neighborhood, zipCode, country, number
        );

        // Usar o builder para evitar o construtor com muitos parâmetros
        return Address.builder()
            .id(id)
            .street(data.street())
            .city(data.city())
            .state(data.state())
            .neighborhood(data.neighborhood())
            .zipCode(data.zipCode())
            .country(data.country())
            .number(data.number())
            .build();
    }
}
