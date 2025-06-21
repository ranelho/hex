package com.rlti.hex.application.port.input;

import com.rlti.hex.application.core.domain.Address;

public interface InsertAddressToPersonInputPort {
    Address insert(Address address, Long id);
}