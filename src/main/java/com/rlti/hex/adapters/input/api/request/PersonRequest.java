package com.rlti.hex.adapters.input.api.request;

import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.core.domain.enuns.Gender;
import com.rlti.hex.application.core.domain.enuns.MaritalStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;

public record PersonRequest(
        @NotBlank(message = "Nome é obrigatório") String name,
        @CPF(message = "CPF Inválido") String cpf,
        String rg,
        String rgIssuer,
        @Past(message = "Data de nascimento inválida") LocalDate birthDate,
        String nameMother,
        String nameFather,
        String maritalStatus,
        String profession,
        String nationality,
        String gender,
        String emergencyContact,
        String emergencyPhone,
        List<@Valid AddressRequest> addresses,
        List<@Valid ContactRequest> contacts,
        List<@Valid DependentRequest> dependents
) {
    public Fisica toDomain() {
        return Fisica.builder()
                .name(name)
                .cpf(cpf)
                .rg(rg)
                .rgIssuer(rgIssuer)
                .birthDate(birthDate)
                .nameMother(nameMother)
                .nameFather(nameFather)
                .maritalStatus(maritalStatus != null ? MaritalStatus.fromDescription(maritalStatus) : null)
                .profession(profession)
                .nationality(nationality)
                .gender(gender != null ? Gender.fromDescription(gender) : null)
                .emergencyContact(emergencyContact)
                .emergencyPhone(emergencyPhone)
                .addresses(addresses != null ? addresses.stream().map(AddressRequest::toDomain).toList() : null)
                .contacts(contacts != null ? contacts.stream().map(ContactRequest::toDomain).toList() : null)
                .dependents(dependents != null ? dependents.stream().map(DependentRequest::toDomain).toList() : null)
                .build();
    }
}
