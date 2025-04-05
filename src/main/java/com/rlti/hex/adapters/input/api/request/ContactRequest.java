package com.rlti.hex.adapters.input.api.request;

import jakarta.validation.constraints.Email;

public record ContactRequest(
        Long id,
        @Email
        String email,
        String ddd,
        String telephoneNumber
) {
}
