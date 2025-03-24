package com.rlti.hex.application.core.usecase;

import com.rlti.hex.adapters.input.api.request.PersonUpdateRequest;
import com.rlti.hex.adapters.input.api.response.PersonResponse;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.UpdatePersonInputPort;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import com.rlti.hex.application.port.output.UpdatePersonOutputPort;
import com.rlti.hex.config.aspect.Monitored;
import com.rlti.hex.handler.ResourceNotFoundException;

@UseCase
@Monitored
public class UpdatePersonUseCase implements UpdatePersonInputPort {

    private final FindPersonOutputPort findPersonOutputPort;
    private final UpdatePersonOutputPort updatePersonOutputPort;

    public UpdatePersonUseCase(
            FindPersonOutputPort findPersonOutputPort,
            UpdatePersonOutputPort updatePersonOutputPort
    ) {
        this.findPersonOutputPort = findPersonOutputPort;
        this.updatePersonOutputPort = updatePersonOutputPort;
    }

    @Override
    public PersonResponse update(Long id, PersonUpdateRequest request) {
        var person = findPersonOutputPort.find(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));

        person.update(request);
        var updatedPerson = updatePersonOutputPort.update(person);

        return new PersonResponse(updatedPerson);
    }
}
