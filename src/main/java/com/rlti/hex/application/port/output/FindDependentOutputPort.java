package com.rlti.hex.application.port.output;

import com.rlti.hex.application.core.domain.Dependent;
import com.rlti.hex.application.core.domain.Person;

import java.util.List;
import java.util.Optional;

public interface FindDependentOutputPort {
    Optional<Dependent> find(Long id);

    List<Dependent> findAllByPerson(Person person);
}
