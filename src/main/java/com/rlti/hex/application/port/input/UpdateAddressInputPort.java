package com.rlti.hex.application.port.input;

import com.rlti.hex.adapters.input.api.request.AddressRequest;
import com.rlti.hex.adapters.input.api.response.AddressResponse;

public interface UpdateAddressInputPort {
    AddressResponse updateAddressInputPort(Long idAddress, AddressRequest request);
}
