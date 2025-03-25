package com.rlti.hex.application.core.usecase;

import com.rlti.hex.adapters.input.api.response.AddressResponse;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.FindAddressInputPort;
import com.rlti.hex.application.port.output.FindAddressOutputPort;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import com.rlti.hex.config.aspect.Monitored;
import com.rlti.hex.handler.ResourceNotFoundException;

import java.util.List;
@UseCase
@Monitored
public class FindAddressUseCase implements FindAddressInputPort {

    private final FindAddressOutputPort findAddressOutputPort;
    private final FindPersonOutputPort findPersonOutputPort;

    public FindAddressUseCase(
            FindAddressOutputPort findAddressOutputPort,
            FindPersonOutputPort findPersonOutputPort
    ) {
        this.findAddressOutputPort = findAddressOutputPort;
        this.findPersonOutputPort = findPersonOutputPort;
    }

    @Override
    public AddressResponse findById(Long idAddress) {
        var address = findAddressOutputPort.find(idAddress)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        return new AddressResponse(address);
    }

    @Override
    public List<AddressResponse> findAll(Long idPerson) {
        var person = findPersonOutputPort.find(idPerson)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        var addresses = findAddressOutputPort.findAll(person.getId());
        return AddressResponse.convertList(addresses);
    }
}
