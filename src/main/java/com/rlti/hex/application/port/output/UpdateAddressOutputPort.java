package com.rlti.hex.application.port.output;

import com.rlti.hex.application.core.domain.Address;

public interface UpdateAddressOutputPort {
    Address update(Address address);
}
