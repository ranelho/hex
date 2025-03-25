package com.rlti.hex.application.port.output;

import com.rlti.hex.application.core.domain.Address;

import java.util.List;
import java.util.Optional;

public interface FindAddressOutputPort {
    Optional<Address> find(Long id);

    List<Address> findAll(Long idPerson);
}
