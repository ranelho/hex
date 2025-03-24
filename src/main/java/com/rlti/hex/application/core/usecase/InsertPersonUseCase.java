package com.rlti.hex.application.core.usecase;

import com.rlti.hex.adapters.input.api.request.PersonRequest;
import com.rlti.hex.adapters.input.api.response.PersonResponse;
import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.InsertPersonInputPort;
import com.rlti.hex.application.port.output.InsertPersonOutputPort;
import com.rlti.hex.config.aspect.Monitored;

@UseCase
@Monitored
public class InsertPersonUseCase implements InsertPersonInputPort {

    private final InsertPersonOutputPort outputPort;

    public InsertPersonUseCase(InsertPersonOutputPort outputPort) {
        this.outputPort = outputPort;
    }

    @Override
    public PersonResponse insert(PersonRequest request) {
        var person = new Fisica(request);
        var personSaved = outputPort.insert(person);
        return new PersonResponse(personSaved);
    }
}
