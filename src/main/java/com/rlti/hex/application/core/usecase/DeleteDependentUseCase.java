package com.rlti.hex.application.core.usecase;

import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.DeleteDependentInputPort;
import com.rlti.hex.application.port.output.DeleteDependentOutputPort;
import com.rlti.hex.application.port.output.FindDependentOutputPort;
import com.rlti.hex.config.aspect.Monitored;
import com.rlti.hex.handler.ResourceNotFoundException;

@UseCase
@Monitored
public class DeleteDependentUseCase implements DeleteDependentInputPort {

    private final FindDependentOutputPort findDependentOutputPort;
    private final DeleteDependentOutputPort deleteDependentOutputPort;

    public DeleteDependentUseCase(
            FindDependentOutputPort findDependentOutputPort,
            DeleteDependentOutputPort deleteDependentOutputPort
    ) {
        this.findDependentOutputPort = findDependentOutputPort;
        this.deleteDependentOutputPort = deleteDependentOutputPort;
    }

    @Override
    public void delete(Long id) {
        var dependent = findDependentOutputPort.find(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dependent not found"));
        deleteDependentOutputPort.delete(dependent);
    }
}
