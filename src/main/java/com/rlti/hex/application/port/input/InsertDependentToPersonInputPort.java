package com.rlti.hex.application.port.input;

import com.rlti.hex.application.core.domain.Dependent;

public interface InsertDependentToPersonInputPort {
    Dependent insert(Dependent dependent,Long idPerson);
}