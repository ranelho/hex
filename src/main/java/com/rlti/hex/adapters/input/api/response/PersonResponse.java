package com.rlti.hex.adapters.input.api.response;

import com.rlti.hex.application.core.domain.Fisica;

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
        List<AddressResponse> addresses,
        List<ContactResponse> contacts
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
                        .orElse(List.of()),

                Optional.ofNullable(person.getContacts())
                        .map(ContactResponse::convertList)
                        .orElse(List.of())
        );
    }
}
