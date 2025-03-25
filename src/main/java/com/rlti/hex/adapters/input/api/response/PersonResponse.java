package com.rlti.hex.adapters.input.api.response;

import com.rlti.hex.application.core.domain.Fisica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public record PersonResponse(
        Long id,
        String name,
        String cpf,
        LocalDate birthDate,
        String nameMother,
        String nameFather,
        List<AddressResponse> addresses
) {
    public PersonResponse(Fisica person) {
        this(
                person.getId(),
                person.getName(),
                person.getCpf(),
                person.getBirthDate(),
                person.getNameMother(),
                person.getNameFather(),
                Optional.ofNullable(person.getAddresses())
                        .map(AddressResponse::convertList)
                        .orElse(List.of())
        );
    }
}
