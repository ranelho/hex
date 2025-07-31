package com.rlti.hex.application.core.usecase;

import com.rlti.hex.application.core.domain.Address;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.FindAddressByZipCodeInputPort;
import com.rlti.hex.application.port.output.ValidateAddressOutputPort;
import com.rlti.hex.config.aspect.Monitored;
import com.rlti.hex.handler.ResourceNotFoundException;

@UseCase
@Monitored
public class FindAddressByZipCodeUseCase implements FindAddressByZipCodeInputPort {

    private final ValidateAddressOutputPort validateAddressOutputPort;

    public FindAddressByZipCodeUseCase(ValidateAddressOutputPort validateAddressOutputPort) {
        this.validateAddressOutputPort = validateAddressOutputPort;
    }

    @Override
    public Address getAddressByZipCode(String zipCode) {
        // Valida o CEP usando o serviço de validação
        if (!validateAddressOutputPort.isValidZipCode(zipCode)) {
            throw new IllegalArgumentException("Invalid zip code: " + zipCode);
        }

        // Busca o endereço pelo CEP
        return validateAddressOutputPort.validateAndCompleteAddress(zipCode);
    }
}
