package com.rlti.hex.application.port.input;

import com.rlti.hex.application.core.domain.Address;

public interface UpdateAddressInputPort {
    Address update(Address address, Long id);
}