package com.rlti.hex.adapters.input.api.response;

import com.rlti.hex.application.core.domain.Fisica;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public record PersonResponse(
        Long id,
        String name,
        String cpf,
        String rg,
        String rgIssuer,
        LocalDate birthDate,
        String nameMother,
        String nameFather,
        String maritalStatus,
        String profession,
        String nationality,
        String gender,
        String emergencyContact,
        String emergencyPhone,
        List<AddressResponse> addresses,
        List<ContactResponse> contacts,
        List<DependentResponse> dependents
) {
    public PersonResponse(Fisica person) {
        this(
                person.getId(),
                person.getName(),
                person.getCpf(),
                person.getRg(),
                person.getRgIssuer(),
                person.getBirthDate(),
                person.getNameMother(),
                person.getNameFather(),
                person.getMaritalStatus() != null ? person.getMaritalStatus().getDescription() : null,
                person.getProfession(),
                person.getNationality(),
                person.getGender() != null ? person.getGender().getDescription() : null,
                person.getEmergencyContact(),
                person.getEmergencyPhone(),

                Optional.ofNullable(person.getAddresses())
                        .map(AddressResponse::convertList)
                        .orElse(List.of()),

                Optional.of(person.getContacts())
                        .map(ContactResponse::convertList)
                        .orElse(List.of()),

                Optional.of(person.getDependents())
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

    public static List<PersonResponse> convertList(List<Fisica> recentPersons) {
        return recentPersons.stream().map(PersonResponse::new).toList();
    }
}
