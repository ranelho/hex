package com.rlti.hex.application.port.input;

import com.rlti.hex.application.core.domain.Fisica;

public interface InsertPersonInputPort {
    Fisica insert(Fisica request);
}