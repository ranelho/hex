package com.rlti.hex.application.core.usecase;

import com.rlti.hex.application.core.domain.Fisica;
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
    public Fisica update(Fisica request, Long id) {
        var person = findPersonOutputPort.find(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));

        person.update(request);
        
        return updatePersonOutputPort.update(person);
    }
}