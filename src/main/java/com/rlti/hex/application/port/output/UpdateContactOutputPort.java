package com.rlti.hex.application.port.output;

import com.rlti.hex.application.core.domain.Contact;

public interface UpdateContactOutputPort {
    Contact update(Contact contact);
}
