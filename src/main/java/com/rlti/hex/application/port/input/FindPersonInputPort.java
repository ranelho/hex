package com.rlti.hex.application.port.input;

import com.rlti.hex.adapters.input.api.PageResult;
import com.rlti.hex.adapters.input.api.response.PersonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindPersonInputPort {
    PersonResponse find(Long id);

    PageResult<PersonResponse> findAll(Pageable pageable);
}
