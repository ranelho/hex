package com.rlti.hex.application.core.usecase;

import com.rlti.hex.application.core.domain.Contact;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.UpdateContactInputPort;
import com.rlti.hex.application.port.output.FindContactOutputPort;
import com.rlti.hex.application.port.output.UpdateContactOutputPort;
import com.rlti.hex.config.aspect.Monitored;

@UseCase
@Monitored
public class UpdateContactUseCase implements UpdateContactInputPort {

    private final UpdateContactOutputPort updateContactOutputPort;
    private final FindContactOutputPort findContactOutputPort;

    public UpdateContactUseCase(
            UpdateContactOutputPort updateContactOutputPort,
            FindContactOutputPort findContactOutputPort
    ) {
        this.updateContactOutputPort = updateContactOutputPort;
        this.findContactOutputPort = findContactOutputPort;
    }

    @Override
    public Contact update(Contact request, Long id) {
        var contact = findContactOutputPort.find(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
        
        contact.update(request);
        
        return updateContactOutputPort.update(contact);
    }
}