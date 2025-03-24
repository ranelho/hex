package com.rlti.hex.application.port.output;

import com.rlti.hex.application.core.domain.Fisica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FindPersonOutputPort {
    Optional<Fisica> find(Long id);

    Page<Fisica> findAll(Pageable pageable);
}
