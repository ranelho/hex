package com.rlti.hex.adapters.input.api.response;

import com.rlti.hex.application.core.domain.Contact;

import java.util.List;

public record ContactResponse(
        Long id,
        String email,
        String ddd,
        String telephoneNumber
) {
    public ContactResponse(Contact contact) {
        this(contact.getId(), contact.getEmail(), contact.getDdd(), contact.getTelephoneNumber());
    }

    public static List<ContactResponse> convertList(List<Contact> contacts) {
        return contacts.stream().map(ContactResponse::new).toList();
    }
}
