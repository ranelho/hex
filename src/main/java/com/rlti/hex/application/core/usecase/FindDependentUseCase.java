package com.rlti.hex.application.core.usecase;

import com.rlti.hex.adapters.input.api.response.DependentResponse;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.FindDependentInputPort;
import com.rlti.hex.application.port.output.FindDependentOutputPort;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import com.rlti.hex.config.aspect.Monitored;
import com.rlti.hex.handler.ResourceNotFoundException;

import java.util.List;

@UseCase
@Monitored
public class FindDependentUseCase implements FindDependentInputPort {

    private final FindDependentOutputPort findDependentOutputPort;
    private final FindPersonOutputPort findPersonOutputPort;

    public FindDependentUseCase(
            FindDependentOutputPort findDependentOutputPort,
            FindPersonOutputPort findPersonOutputPort
    ) {
        this.findDependentOutputPort = findDependentOutputPort;
        this.findPersonOutputPort = findPersonOutputPort;
    }

    @Override
    public DependentResponse find(Long id) {
        var dependent = findDependentOutputPort.find(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dependent not found"));
        return new DependentResponse(dependent);
    }

    @Override
    public List<DependentResponse> list(Long idPerson) {
        var person = findPersonOutputPort.find(idPerson)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        var dependents = findDependentOutputPort.findAllByPerson(person);
        return DependentResponse.convertList(dependents);
    }
}
