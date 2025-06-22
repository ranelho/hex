package com.rlti.hex.application.core.usecase;

import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.DeletePersonInputPort;
import com.rlti.hex.application.port.output.DeletePersonOutputPort;
import com.rlti.hex.application.port.output.FindPersonOutputPort;

@UseCase
public class DeletePersonUseCase implements DeletePersonInputPort {

    private final DeletePersonOutputPort deletePersonOutputPort;
    private final FindPersonOutputPort findPersonOutputPort;

    public DeletePersonUseCase(DeletePersonOutputPort deletePersonOutputPort, FindPersonOutputPort findPersonOutputPort) {
        this.deletePersonOutputPort = deletePersonOutputPort;
        this.findPersonOutputPort = findPersonOutputPort;
    }

    @Override
    public void delete(Long id) {
        var person = findPersonOutputPort.find(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        deletePersonOutputPort.delete(person);
    }
}
