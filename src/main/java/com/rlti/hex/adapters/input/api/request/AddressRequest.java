package com.rlti.hex.adapters.input.api.request;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
        Long id,
        String street,
        @NotBlank(message = "City is mandatory")
        String city,
        String state,
        String neighborhood,
        @NotBlank(message = "Zip code is mandatory")
        String zipCode,
        String country,
        String number
) {
}
