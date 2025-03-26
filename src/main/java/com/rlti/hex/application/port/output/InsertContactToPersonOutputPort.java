package com.rlti.hex.application.port.output;

import com.rlti.hex.application.core.domain.Contact;

public interface InsertContactToPersonOutputPort {
    Contact insert(Contact contact);
}
