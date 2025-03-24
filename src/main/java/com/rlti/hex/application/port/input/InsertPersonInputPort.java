package com.rlti.hex.application.port.input;

import com.rlti.hex.adapters.input.api.request.PersonRequest;
import com.rlti.hex.adapters.input.api.response.PersonResponse;
import jakarta.validation.Valid;

public interface InsertPersonInputPort {
    PersonResponse insert(@Valid PersonRequest request);
}
