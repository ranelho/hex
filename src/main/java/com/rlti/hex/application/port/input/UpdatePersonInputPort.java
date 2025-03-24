package com.rlti.hex.application.port.input;

import com.rlti.hex.adapters.input.api.request.PersonUpdateRequest;
import com.rlti.hex.adapters.input.api.response.PersonResponse;

public interface UpdatePersonInputPort {
    PersonResponse update(Long id, PersonUpdateRequest request);
}
