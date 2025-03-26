package com.rlti.hex.application.port.output;

import com.rlti.hex.application.core.domain.Contact;

public interface DeleteContactOutputPort {
    void delete(Contact contact);
}
