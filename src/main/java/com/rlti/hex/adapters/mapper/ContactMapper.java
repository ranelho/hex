package com.rlti.hex.adapters.mapper;

import com.rlti.hex.adapters.output.entity.ContactEntity;
import com.rlti.hex.application.core.domain.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public Contact toModel(ContactEntity entity) {
        if (entity == null) {
            return null;
        }

        Contact contact = new Contact();
        contact.setId(entity.getId());
        contact.setEmail(entity.getEmail());
        contact.setTelephoneNumber(entity.getTelephoneNumber());
        contact.setDdd(entity.getDdd());

        return contact;
    }

    public ContactEntity toEntity(Contact model) {
        if (model == null) {
            return null;
        }

        ContactEntity entity = new ContactEntity();
        entity.setId(model.getId());
        entity.setEmail(model.getEmail());
        entity.setTelephoneNumber(model.getTelephoneNumber());
        entity.setDdd(model.getDdd());

        return entity;
    }
}