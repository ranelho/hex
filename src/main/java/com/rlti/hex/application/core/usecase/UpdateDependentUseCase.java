package com.rlti.hex.application.core.usecase;

import com.rlti.hex.application.core.domain.Dependent;
import com.rlti.hex.application.core.domain.enuns.DependentType;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.UpdateDependentInputPort;
import com.rlti.hex.application.port.output.FindDependentOutputPort;
import com.rlti.hex.application.port.output.UpdateDependentOutputPort;
import com.rlti.hex.config.aspect.Monitored;

import java.time.LocalDate;

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
    public Dependent update(Long id, String name, String cpf, LocalDate birthDate, String dependentType) {
        var dependent = findDependentOutputPort.find(id)
                .orElseThrow(() -> new RuntimeException("Dependent not found"));
        
        dependent.update(
            name,
            cpf,
            birthDate,
            DependentType.fromDescription(dependentType)
        );
        
        return updateDependentOutputPort.update(dependent);
    }
}