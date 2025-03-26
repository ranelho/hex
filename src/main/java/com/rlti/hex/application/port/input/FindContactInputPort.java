package com.rlti.hex.application.port.input;

import com.rlti.hex.adapters.input.api.response.ContactResponse;

import java.util.List;

public interface FindContactInputPort {
    ContactResponse find(Long id);

    List<ContactResponse> findAllByPerson(Long idPerson);
}
