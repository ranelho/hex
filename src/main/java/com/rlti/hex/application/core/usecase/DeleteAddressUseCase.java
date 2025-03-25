package com.rlti.hex.application.core.usecase;

import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.DeleteAddressInputPort;
import com.rlti.hex.application.port.output.DeleteAddressOutputPort;
import com.rlti.hex.application.port.output.FindAddressOutputPort;
import com.rlti.hex.config.aspect.Monitored;

@UseCase
@Monitored
public class DeleteAddressUseCase implements DeleteAddressInputPort {

    private final DeleteAddressOutputPort deleteAddressOutputPort;
    private final FindAddressOutputPort findAddressOutputPort;

    public DeleteAddressUseCase(
            DeleteAddressOutputPort deleteAddressOutputPort,
            FindAddressOutputPort findAddressOutputPort
    ) {
        this.deleteAddressOutputPort = deleteAddressOutputPort;
        this.findAddressOutputPort = findAddressOutputPort;
    }

    @Override
    public void delete(Long idAddress) {
        var address = findAddressOutputPort.find(idAddress)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        deleteAddressOutputPort.delete(address);
    }
}
