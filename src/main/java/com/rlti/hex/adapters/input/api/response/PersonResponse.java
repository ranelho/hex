package com.rlti.hex.adapters.input.api.response;

import com.rlti.hex.application.core.domain.Fisica;

import java.time.LocalDate;

public record PersonResponse(
        Long id,
        String name,
        String cpf,
        LocalDate birthDate
) {
    public PersonResponse(Fisica person) {
        this(person.getId(), person.getName(), person.getCpf(), person.getBirthDate());
    }
}
