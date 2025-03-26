package com.rlti.hex.application.core.usecase;

import com.rlti.hex.adapters.input.api.request.ContactRequest;
import com.rlti.hex.adapters.input.api.response.ContactResponse;
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
    public ContactResponse insert(Long idPerson, ContactRequest request) {
        var fisica = findPersonOutputPort.find(idPerson)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        var contact = new Contact(fisica, request);

        var contactSaved = insertContactToPersonOutputPort.insert(contact);
        return new ContactResponse(contactSaved);
    }
}
