package com.rlti.hex.application.port.input;

import com.rlti.hex.adapters.input.api.response.PageResult;
import com.rlti.hex.adapters.input.api.response.PersonResponse;
import com.rlti.hex.application.core.domain.Fisica;

public interface FindPersonInputPort {
    PersonResponse find(Long id);

    PageResult<Fisica> findAll(int page, int size);
}
