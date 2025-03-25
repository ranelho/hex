package com.rlti.hex.application.port.input;

import com.rlti.hex.adapters.input.api.request.AddressRequest;
import com.rlti.hex.adapters.input.api.response.AddressResponse;

public interface InsertAddressToPersonInputPort {
    AddressResponse insert(Long idPerson, AddressRequest request);
}
