package com.rlti.hex.application.port.input;

import com.rlti.hex.application.core.domain.Contact;

public interface InsertContactToPersonInputPort {
    Contact insert(Contact contact, Long id);
}