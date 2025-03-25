package com.rlti.hex.application.port.output;

import com.rlti.hex.application.core.domain.Address;

public interface InsertAddressToPersonOutputPort {
    Address insert(Address address);
}
