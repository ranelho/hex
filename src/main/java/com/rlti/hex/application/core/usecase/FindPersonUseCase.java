package com.rlti.hex.application.core.usecase;

import com.rlti.hex.adapters.input.api.response.PersonResponse;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.FindPersonInputPort;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import com.rlti.hex.config.aspect.Monitored;
import com.rlti.hex.handler.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Monitored
@UseCase
public class FindPersonUseCase implements FindPersonInputPort {

    private final FindPersonOutputPort findPersonOutputPort;

    public FindPersonUseCase(FindPersonOutputPort findPersonOutputPort) {
        this.findPersonOutputPort = findPersonOutputPort;
    }

    @Override
    public PersonResponse find(Long id) {
        var person = findPersonOutputPort.find(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        return new PersonResponse(person);
    }

    @Override
    public Page<PersonResponse> findAll(Pageable pageable) {
        var persons = findPersonOutputPort.findAll(pageable);
        return PersonResponse.convertPage(persons, pageable);
    }
}
