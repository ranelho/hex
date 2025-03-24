package com.rlti.hex.adapters.input.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record PersonUpdateRequest(
        @NotBlank(message = "Name is required")
        String name,
        @Past(message = "Invalid birth date")
        LocalDate birthDate,
        String nameMother,
        String nameFather
) {
}
