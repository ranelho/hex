package com.rlti.hex.application.core.usecase;

import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.port.input.AddressEnrichmentInputPort;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.InsertPersonInputPort;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import com.rlti.hex.application.port.output.InsertPersonOutputPort;
import com.rlti.hex.config.aspect.Monitored;
import com.rlti.hex.handler.DuplicidadeException;


@UseCase
@Monitored
public class InsertPersonUseCase implements InsertPersonInputPort {

    private final InsertPersonOutputPort outputPort;
    private final FindPersonOutputPort findPersonOutputPort;
    private final AddressEnrichmentInputPort addressEnrichmentService;

    public InsertPersonUseCase(
            InsertPersonOutputPort outputPort,
            FindPersonOutputPort findPersonOutputPort,
            AddressEnrichmentInputPort addressEnrichmentService
    ) {
        this.outputPort = outputPort;
        this.findPersonOutputPort = findPersonOutputPort;
        this.addressEnrichmentService = addressEnrichmentService;
    }

    @Override
    public Fisica insert(Fisica request) {
        if (findPersonOutputPort.exists(request.getCpf()))
            throw new DuplicidadeException("Person already exists");

        // Complementa os endereços se existirem, usando o serviço dedicado
        if (request.getAddresses() != null && !request.getAddresses().isEmpty()) {
            addressEnrichmentService.complementAddressesData(request.getAddresses());
        }

        return outputPort.insert(request);
    }
}