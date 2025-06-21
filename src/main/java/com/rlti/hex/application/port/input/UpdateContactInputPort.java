package com.rlti.hex.application.port.input;

import com.rlti.hex.application.core.domain.Contact;

public interface UpdateContactInputPort {
    Contact update(Contact contact, Long id);
}