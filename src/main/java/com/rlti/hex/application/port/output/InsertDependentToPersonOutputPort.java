package com.rlti.hex.application.port.output;

import com.rlti.hex.application.core.domain.Dependent;

public interface InsertDependentToPersonOutputPort {
    Dependent insert(Dependent dependent);
}
