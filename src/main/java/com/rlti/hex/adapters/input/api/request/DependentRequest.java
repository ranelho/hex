package com.rlti.hex.adapters.input.api.request;

import com.rlti.hex.application.core.domain.Dependent;
import com.rlti.hex.application.core.domain.enuns.DependentType;

import java.time.LocalDate;

public record DependentRequest(
        Long id,
        String name,
        String cpf,
        LocalDate birthDate,
        String dependentType
) {
    public Dependent toDomain() {
        return new Dependent(
            id,
            name,
            cpf,
            birthDate,
            DependentType.fromDescription(dependentType)
        );
    }
}
