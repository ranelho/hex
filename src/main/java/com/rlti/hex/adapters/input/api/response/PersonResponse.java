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
        List<ContactResponse> contacts,
        List<DependentResponse> dependents
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
                        .orElse(List.of()),

                Optional.ofNullable(person.getDependents())
                        .map(DependentResponse::convertList)
                        .orElse(List.of())
        );
    }

    public static PageResult<PersonResponse> convertToPageResult(PageResult<Fisica> response) {
        return new PageResult<>(
                response.content().stream().map(PersonResponse::new).toList(),
                response.pageNumber(),
                response.pageSize(),
                response.totalElements()
        );
    }
}
