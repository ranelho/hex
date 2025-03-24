package com.rlti.hex.application.port.output;

import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.core.domain.Person;

public interface InsertPersonOutputPort {
    Fisica insert(Person person);
}
