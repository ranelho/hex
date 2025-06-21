package com.rlti.hex.adapters.input.api.request;

import com.rlti.hex.application.core.domain.Fisica;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.List;

public record PersonUpdateRequest(
        @NotBlank(message = "Name is required")
        String name,
        @Past(message = "Invalid birth date")
        LocalDate birthDate,
        String nameMother,
        String nameFather,
        List<@Valid AddressRequest> addresses,
        List<@Valid ContactRequest> contacts,
        List<@Valid DependentRequest> dependents
) {
    public Fisica updateDomain() {
        return Fisica.builder()
                .name(name)
                .birthDate(birthDate)
                .nameMother(nameMother)
                .nameFather(nameFather)
                .addresses(addresses != null ? addresses.stream().map(AddressRequest::toDomain).toList() : null)
                .contacts(contacts != null ? contacts.stream().map(ContactRequest::toDomain).toList() : null)
                .dependents(dependents != null ? dependents.stream().map(DependentRequest::toDomain).toList() : null)
                .build();
    }
}
