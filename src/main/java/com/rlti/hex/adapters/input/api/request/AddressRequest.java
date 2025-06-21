package com.rlti.hex.adapters.input.api.request;

import com.rlti.hex.application.core.domain.Address;
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
        return new Address(
            id,
            street,
            city,
            state,
            neighborhood,
            zipCode,
            country,
            number
        );
    }
}
