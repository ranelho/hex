package com.rlti.hex.application.port.output;

import com.rlti.hex.application.core.domain.Address;

public interface DeleteAddressOutputPort {
    void delete(Address address);
}
