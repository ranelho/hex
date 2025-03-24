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
        List<AddressResponse> addresses
) {
    public PersonResponse(Fisica person) {
        this(
                person.getId(),
                person.getName(),
                person.getCpf(),
                person.getBirthDate(),
                Optional.ofNullable(person.getAddresses())
                        .map(AddressResponse::convertList)
                        .orElse(List.of())
        );
    }

    public static Page<PersonResponse> convertPage(Page<Fisica> persons, Pageable pageable) {
        var personResponses = persons.stream()
                .map(PersonResponse::new)
                .toList();
        return new PageImpl<>(personResponses, pageable, persons.getTotalElements());
    }
}
