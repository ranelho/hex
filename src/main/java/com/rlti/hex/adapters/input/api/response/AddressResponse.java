package com.rlti.hex.adapters.input.api.response;

import com.rlti.hex.application.core.domain.Address;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AddressResponse(
        Long id,
        String street,
        String city,
        String state,
        String zipCode,
        String country,
        String number
) {
    public AddressResponse(Address address) {
        this(address.getId(), address.getStreet(), address.getCity(), address.getState(), address.getZipCode(), address.getCountry(), address.getNumber());
    }

    public static List<AddressResponse> convertList(List<Address> addresses) {
        return addresses.stream().map(AddressResponse::new).toList();
    }
}
