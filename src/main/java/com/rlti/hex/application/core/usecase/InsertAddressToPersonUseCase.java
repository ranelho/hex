package com.rlti.hex.application.core.usecase;

import com.rlti.hex.adapters.input.api.request.AddressRequest;
import com.rlti.hex.adapters.input.api.response.AddressResponse;
import com.rlti.hex.application.core.domain.Address;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.InsertAddressToPersonInputPort;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import com.rlti.hex.application.port.output.InsertAddressToPersonOutputPort;
import com.rlti.hex.config.aspect.Monitored;
import com.rlti.hex.handler.ResourceNotFoundException;

@UseCase
@Monitored
public class InsertAddressToPersonUseCase implements InsertAddressToPersonInputPort {

    private final InsertAddressToPersonOutputPort insertAddressToPersonOutputPort;
    private final FindPersonOutputPort findPersonByIdUseCase;

    public InsertAddressToPersonUseCase(
            InsertAddressToPersonOutputPort insertAddressToPersonOutputPort,
            FindPersonOutputPort findPersonByIdUseCase
    ) {
        this.insertAddressToPersonOutputPort = insertAddressToPersonOutputPort;
        this.findPersonByIdUseCase = findPersonByIdUseCase;
    }

    @Override
    public AddressResponse insert(Long idPerson, AddressRequest request) {
        var person = findPersonByIdUseCase.findPerson(idPerson)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        var address = new Address(person, request);
        var addressSaved = insertAddressToPersonOutputPort.insert(address);
        return new AddressResponse(addressSaved);
    }
}
