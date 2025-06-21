package com.rlti.hex.application.core.usecase;

import com.rlti.hex.application.core.domain.Address;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.UpdateAddressInputPort;
import com.rlti.hex.application.port.output.FindAddressOutputPort;
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
    public Address update(Address address, Long idAddress) {
        var addressFound = findAddressOutputPort.find(idAddress)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        addressFound.update(address);

        return updateAddressOutputPort.update(address);
    }
}