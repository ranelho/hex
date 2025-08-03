package com.rlti.hex.application.core.usecase;

import com.rlti.hex.application.core.domain.Address;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.FindAddressByZipCodeInputPort;
import com.rlti.hex.application.port.output.ValidateAddressOutputPort;
import com.rlti.hex.config.aspect.Monitored;

@UseCase
@Monitored
public class FindAddressByZipCodeUseCase implements FindAddressByZipCodeInputPort {

    private final ValidateAddressOutputPort validateAddressOutputPort;

    public FindAddressByZipCodeUseCase(ValidateAddressOutputPort validateAddressOutputPort) {
        this.validateAddressOutputPort = validateAddressOutputPort;
    }

    @Override
    public Address getAddressByZipCode(String zipCode) {
        if (!validateAddressOutputPort.isValidZipCode(zipCode)) {
            throw new IllegalArgumentException("Invalid zip code: " + zipCode);
        }
        return validateAddressOutputPort.validateAndCompleteAddress(zipCode);
    }
}
