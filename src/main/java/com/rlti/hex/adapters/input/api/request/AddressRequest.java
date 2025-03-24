package com.rlti.hex.adapters.input.api.request;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
        String street,
        @NotBlank(message = "City is mandatory")
        String city,
        String state,
        @NotBlank(message = "Zip code is mandatory")
        String zipCode,
        String country,
        String number
) {
}
