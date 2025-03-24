package com.rlti.hex.application.port.input;

import com.rlti.hex.adapters.input.api.response.PersonResponse;

public interface FindPersonInputPort {
    PersonResponse find(Long id);
}
