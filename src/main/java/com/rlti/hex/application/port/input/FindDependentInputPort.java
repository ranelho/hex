package com.rlti.hex.application.port.input;

import com.rlti.hex.adapters.input.api.response.DependentResponse;

import java.util.List;

public interface FindDependentInputPort {
    DependentResponse find(Long id);

    List<DependentResponse> list(Long idPerson);
}
