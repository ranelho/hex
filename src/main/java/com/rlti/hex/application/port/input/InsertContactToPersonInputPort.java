package com.rlti.hex.application.port.input;

import com.rlti.hex.adapters.input.api.request.ContactRequest;
import com.rlti.hex.adapters.input.api.response.ContactResponse;
import jakarta.validation.Valid;

public interface InsertContactToPersonInputPort {
    ContactResponse insert(Long idPerson, @Valid ContactRequest request);
}
