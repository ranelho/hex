package com.rlti.hex.application.core.usecase;

import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.DeleteContactInputPort;
import com.rlti.hex.application.port.output.DeleteContactOutputPort;
import com.rlti.hex.application.port.output.FindContactOutputPort;
import com.rlti.hex.config.aspect.Monitored;

@UseCase
@Monitored
public class DeleteContactUseCase implements DeleteContactInputPort {

    private final DeleteContactOutputPort deleteContactOutputPort;
    private final FindContactOutputPort findContactOutputPort;

    public DeleteContactUseCase(
            DeleteContactOutputPort deleteContactOutputPort,
            FindContactOutputPort findContactOutputPort
    ) {
        this.deleteContactOutputPort = deleteContactOutputPort;
        this.findContactOutputPort = findContactOutputPort;
    }

    @Override
    public void delete(Long id) {
        var contact = findContactOutputPort.find(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
        deleteContactOutputPort.delete(contact);
    }
}
