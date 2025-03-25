package com.rlti.hex.application.port.input;

import com.rlti.hex.adapters.input.api.response.AddressResponse;

import java.util.List;

public interface FindAddressInputPort {
    AddressResponse findById(Long idAddress);

    List<AddressResponse> findAll(Long idPerson);
}
