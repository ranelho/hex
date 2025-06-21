package com.rlti.hex.adapters.input.api.request;

import com.rlti.hex.application.core.domain.Contact;
import jakarta.validation.constraints.Email;

public record ContactRequest(
        Long id,
        @Email
        String email,
        String ddd,
        String telephoneNumber
) {
    public Contact toDomain() {
        return new Contact(
            id,
            email,
            ddd,
            telephoneNumber
        );
    }
}
