package com.rlti.hex.application.port.output;

import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.core.domain.Person;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FindPersonOutputPort {
    Optional<Fisica> find(Long id);

    Page<Fisica> findAll(Pageable pageable);

    Optional<Person> findPerson(Long id);

    boolean exists(@CPF(message = "Invalid CPF") String cpf);
}
