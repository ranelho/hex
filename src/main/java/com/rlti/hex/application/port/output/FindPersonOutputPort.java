package com.rlti.hex.application.port.output;

import com.rlti.hex.application.core.domain.Fisica;

import java.util.Optional;

public interface FindPersonOutputPort {
    Optional<Fisica> find(Long id);
}
