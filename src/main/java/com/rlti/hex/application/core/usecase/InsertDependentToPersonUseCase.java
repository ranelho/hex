package com.rlti.hex.application.core.usecase;

import com.rlti.hex.adapters.input.api.request.DependentRequest;
import com.rlti.hex.adapters.input.api.response.DependentResponse;
import com.rlti.hex.application.core.domain.Dependent;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.InsertDependentToPersonInputPort;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import com.rlti.hex.application.port.output.InsertDependentToPersonOutputPort;
import com.rlti.hex.config.aspect.Monitored;

@UseCase
@Monitored
public class InsertDependentToPersonUseCase implements InsertDependentToPersonInputPort {

    private final FindPersonOutputPort findPersonOutputPort;
    private final InsertDependentToPersonOutputPort insertDependentToPersonOutputPort;

    public InsertDependentToPersonUseCase(
            FindPersonOutputPort findPersonOutputPort,
            InsertDependentToPersonOutputPort insertDependentToPersonOutputPort
    ) {
        this.findPersonOutputPort = findPersonOutputPort;
        this.insertDependentToPersonOutputPort = insertDependentToPersonOutputPort;
    }

    @Override
    public DependentResponse insert(Long idPerson, DependentRequest dependentRequest) {
        var fisica = findPersonOutputPort.find(idPerson)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        var dependent = new Dependent(fisica, dependentRequest);
        var dependentSaved = insertDependentToPersonOutputPort.insert(dependent);
        return new DependentResponse(dependentSaved);
    }
}
