package com.rlti.hex.application.port.output;

import com.rlti.hex.application.core.domain.Contact;

import java.util.List;
import java.util.Optional;

public interface FindContactOutputPort {
    Optional<Contact> find(Long id);

    List<Contact> findAllByPerson(Long id);
}
