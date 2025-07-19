package com.rlti.hex.application.core.usecase;

import com.rlti.hex.adapters.input.api.response.PageResult;
import com.rlti.hex.adapters.input.api.response.PersonResponse;
import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.FindPersonInputPort;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import com.rlti.hex.config.aspect.Cached;
import com.rlti.hex.config.aspect.Monitored;
import com.rlti.hex.handler.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;

@Monitored(level = Monitored.LogLevel.DETAILED, logParameters = true, slowExecutionThresholdMs = 500)
@Cached(cacheName = "persons")
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
    public PageResult<Fisica> findAll(int page, int size) {
        var persons = findPersonOutputPort.findAll(page, size);
        return PageResult.from(persons);
    }
}
