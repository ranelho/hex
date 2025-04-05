package com.rlti.hex.adapters.input.api.request;

import java.time.LocalDate;

public record DependentRequest(
        Long id,
        String name,
        String cpf,
        LocalDate birthDate,
        String dependentType
) {
}
