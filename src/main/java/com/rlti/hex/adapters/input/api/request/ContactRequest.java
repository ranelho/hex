package com.rlti.hex.adapters.input.api.request;

public record ContactRequest(
        Long id,
        String email,
        String ddd,
        String telephoneNumber
) {
}
