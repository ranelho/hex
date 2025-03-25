package com.rlti.hex.adapters.input.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;

public record PersonRequest(
        @NotBlank(message = "Name is required")
        String name,
        @CPF(message = "Invalid CPF")
        String cpf,
        @Past(message = "Invalid birth date")
        LocalDate birthDate,
        String nameMother,
        String nameFather,
        List<@Valid AddressRequest> addresses
) {
}
