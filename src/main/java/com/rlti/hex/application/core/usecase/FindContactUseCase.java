package com.rlti.hex.application.core.usecase;

import com.rlti.hex.adapters.input.api.response.ContactResponse;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.FindContactInputPort;
import com.rlti.hex.application.port.output.FindContactOutputPort;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import com.rlti.hex.config.aspect.Monitored;

import java.util.List;

@UseCase
@Monitored
public class FindContactUseCase implements FindContactInputPort {

    private final FindContactOutputPort findContactOutputPort;
    private final FindPersonOutputPort findPersonOutputPort;

    public FindContactUseCase(
            FindContactOutputPort findContactOutputPort,
            FindPersonOutputPort findPersonOutputPort
    ) {
        this.findContactOutputPort = findContactOutputPort;
        this.findPersonOutputPort = findPersonOutputPort;
    }

    @Override
    public ContactResponse find(Long id) {
        var contact = findContactOutputPort.find(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
        return new ContactResponse(contact);
    }

    @Override
    public List<ContactResponse> findAllByPerson(Long idPerson) {
        var person = findPersonOutputPort.find(idPerson)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        var contacts = findContactOutputPort.findAllByPerson(person.getId());
        return ContactResponse.convertList(contacts);
    }
}
