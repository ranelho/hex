package com.rlti.hex.adapters.input.api.response;

import com.rlti.hex.application.core.domain.Dependent;

import java.time.LocalDate;
import java.util.List;

public record DependentResponse(
        Long id,
        String name,
        String cpf,
        LocalDate birthDate,
        String dependentType
) {
    public DependentResponse(Dependent dependent) {
        this(
                dependent.getId(),
                dependent.getName(),
                dependent.getCpf(),
                dependent.getBirthDate(),
                dependent.getDependentType() != null ? dependent.getDependentType().getDescription(): null
        );
    }

    public static List<DependentResponse> convertList(List<Dependent> dependents) {
        return dependents.stream().map(DependentResponse::new).toList();
    }
}
