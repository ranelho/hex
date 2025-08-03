package com.rlti.hex.application.core.usecase;

import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.core.domain.event.PersonCreatedEvent;
import com.rlti.hex.application.port.input.AddressEnrichmentInputPort;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.InsertPersonInputPort;
import com.rlti.hex.application.port.output.EventPublisherOutputPort;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import com.rlti.hex.application.port.output.InsertPersonOutputPort;
import com.rlti.hex.config.aspect.Monitored;
import com.rlti.hex.handler.DuplicidadeException;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;


@Slf4j
@UseCase
@Monitored
public class InsertPersonUseCase implements InsertPersonInputPort {

    private final InsertPersonOutputPort outputPort;
    private final FindPersonOutputPort findPersonOutputPort;
    private final AddressEnrichmentInputPort addressEnrichmentService;
    private final EventPublisherOutputPort eventPublisherOutputPort;

    public InsertPersonUseCase(
            InsertPersonOutputPort outputPort,
            FindPersonOutputPort findPersonOutputPort,
            AddressEnrichmentInputPort addressEnrichmentService,
            EventPublisherOutputPort eventPublisherOutputPort
    ) {
        this.outputPort = outputPort;
        this.findPersonOutputPort = findPersonOutputPort;
        this.addressEnrichmentService = addressEnrichmentService;
        this.eventPublisherOutputPort = eventPublisherOutputPort;
    }

    @Override
    public Fisica insert(Fisica request) {
        if (findPersonOutputPort.exists(request.getCpf()))
            throw new DuplicidadeException("Pessoa ja tem cadastro!");

        if (request.getAddresses() != null && !request.getAddresses().isEmpty()) {
            addressEnrichmentService.complementAddressesData(request.getAddresses());
        }

        Fisica savedPerson = outputPort.insert(request);
        
        try {
            String email = getPersonEmail(savedPerson);
            PersonCreatedEvent event = new PersonCreatedEvent(
                savedPerson.getId(),
                savedPerson.getName(),
                savedPerson.getCpf(),
                email
            );
            
            eventPublisherOutputPort.publishPersonCreatedEvent(event);
            log.info("Person created event published for person ID: {}", savedPerson.getId());
            
        } catch (Exception e) {
            log.error("Failed to publish person created event for person ID: {}", savedPerson.getId(), e);
            
        }
        
        return savedPerson;
    }
    
    private String getPersonEmail(Fisica person) {
        return Optional.of(person.getContacts())
            .filter(contacts -> !contacts.isEmpty())
            .map(contacts -> contacts.getFirst().getEmail())
            .orElse(null);
    }
}