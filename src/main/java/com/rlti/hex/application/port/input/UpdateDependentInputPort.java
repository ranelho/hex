package com.rlti.hex.application.port.input;

import com.rlti.hex.adapters.input.api.request.DependentRequest;
import com.rlti.hex.adapters.input.api.response.DependentResponse;

public interface UpdateDependentInputPort {
    DependentResponse update(Long id, DependentRequest dependentRequest);
}
