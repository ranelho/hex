package com.rlti.hex.application.core.usecase;

import com.rlti.hex.application.core.domain.Address;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.InsertAddressToPersonInputPort;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import com.rlti.hex.application.port.output.InsertAddressToPersonOutputPort;
import com.rlti.hex.application.port.input.AddressEnrichmentInputPort;
import com.rlti.hex.config.aspect.Monitored;
import com.rlti.hex.handler.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Supplier;

@UseCase
@Monitored
public class InsertAddressToPersonUseCase implements InsertAddressToPersonInputPort {

    private static final Logger logger = LoggerFactory.getLogger(InsertAddressToPersonUseCase.class);

    private final InsertAddressToPersonOutputPort insertAddressToPersonOutputPort;
    private final FindPersonOutputPort findPersonByIdUseCase;
    private final AddressEnrichmentInputPort addressEnrichmentService;

    public InsertAddressToPersonUseCase(
            InsertAddressToPersonOutputPort insertAddressToPersonOutputPort,
            FindPersonOutputPort findPersonByIdUseCase,
            AddressEnrichmentInputPort addressEnrichmentService
    ) {
        this.insertAddressToPersonOutputPort = insertAddressToPersonOutputPort;
        this.findPersonByIdUseCase = findPersonByIdUseCase;
        this.addressEnrichmentService = addressEnrichmentService;
    }

    @Override
    public Address insert(Address address, Long idPerson) {
        var person = findPersonByIdUseCase.findPerson(idPerson)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));

        // Complementa o endereço com dados do serviço externo, usando o serviço dedicado
        addressEnrichmentService.complementAddressData(address);

        address.setPerson(person);

        return insertAddressToPersonOutputPort.insert(address);
    }
}