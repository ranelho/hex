package com.rlti.hex.application.port.input;

import com.rlti.hex.application.core.domain.Dependent;
import com.rlti.hex.application.core.domain.enuns.DependentType;

import java.time.LocalDate;

public interface UpdateDependentInputPort {
    Dependent update(Long id, String name, String cpf, LocalDate birthDate, String dependentType);
}