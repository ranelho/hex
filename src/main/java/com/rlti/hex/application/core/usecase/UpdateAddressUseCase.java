package com.rlti.hex.application.core.usecase;

import com.rlti.hex.adapters.input.api.request.AddressRequest;
import com.rlti.hex.adapters.input.api.response.AddressResponse;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.UpdateAddressInputPort;
import com.rlti.hex.application.port.output.FindAddressOutputPort;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import com.rlti.hex.application.port.output.UpdateAddressOutputPort;
import com.rlti.hex.config.aspect.Monitored;

@UseCase
@Monitored
public class UpdateAddressUseCase implements UpdateAddressInputPort {

    private final UpdateAddressOutputPort updateAddressOutputPort;
    private final FindAddressOutputPort findAddressOutputPort;

    public UpdateAddressUseCase(
            UpdateAddressOutputPort updateAddressOutputPort,
            FindAddressOutputPort findAddressOutputPort
    ) {
        this.updateAddressOutputPort = updateAddressOutputPort;
        this.findAddressOutputPort = findAddressOutputPort;
    }

    @Override
    public AddressResponse updateAddressInputPort(Long idAddress, AddressRequest request) {
        var address = findAddressOutputPort.find(idAddress)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        address.update(request);
        var addressSaved = updateAddressOutputPort.update(address);
        return new AddressResponse(addressSaved);
    }
}
