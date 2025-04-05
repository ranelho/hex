package com.rlti.hex.application.core.usecase;

import com.rlti.hex.adapters.input.api.request.ContactRequest;
import com.rlti.hex.adapters.input.api.request.DependentRequest;
import com.rlti.hex.adapters.input.api.response.DependentResponse;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.UpdateDependentInputPort;
import com.rlti.hex.application.port.output.FindDependentOutputPort;
import com.rlti.hex.application.port.output.UpdateDependentOutputPort;
import com.rlti.hex.config.aspect.Monitored;

@UseCase
@Monitored
public class UpdateDependentUseCase implements UpdateDependentInputPort {

    private final FindDependentOutputPort findDependentOutputPort;
    private final UpdateDependentOutputPort updateDependentOutputPort;

    public UpdateDependentUseCase(
            FindDependentOutputPort findDependentOutputPort,
            UpdateDependentOutputPort updateDependentOutputPort
    ) {
        this.findDependentOutputPort = findDependentOutputPort;
        this.updateDependentOutputPort = updateDependentOutputPort;
    }

    @Override
    public DependentResponse update(Long id, DependentRequest dependentRequest) {
        return null;
    }
}
