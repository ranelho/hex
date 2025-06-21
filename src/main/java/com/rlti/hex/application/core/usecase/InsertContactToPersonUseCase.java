package com.rlti.hex.application.core.usecase;

import com.rlti.hex.application.core.domain.Contact;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.InsertContactToPersonInputPort;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import com.rlti.hex.application.port.output.InsertContactToPersonOutputPort;
import com.rlti.hex.config.aspect.Monitored;

@UseCase
@Monitored
public class InsertContactToPersonUseCase implements InsertContactToPersonInputPort {

    private final InsertContactToPersonOutputPort insertContactToPersonOutputPort;
    private final FindPersonOutputPort findPersonOutputPort;

    public InsertContactToPersonUseCase(
            InsertContactToPersonOutputPort insertContactToPersonOutputPort,
            FindPersonOutputPort findPersonOutputPort
    ) {
        this.insertContactToPersonOutputPort = insertContactToPersonOutputPort;
        this.findPersonOutputPort = findPersonOutputPort;
    }

    @Override
    public Contact insert(Contact contact, Long idPerson) {
        var fisica = findPersonOutputPort.find(idPerson)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        
        contact.setFisica(fisica);
        
        return insertContactToPersonOutputPort.insert(contact);
    }
}