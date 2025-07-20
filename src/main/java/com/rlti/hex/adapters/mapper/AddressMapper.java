package com.rlti.hex.adapters.mapper;

import com.rlti.hex.adapters.output.entity.AddressEntity;
import com.rlti.hex.application.core.domain.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toModel(AddressEntity entity) {
        if (entity == null) {
            return null;
        }

        Address address = new Address();
        address.setId(entity.getId());
        address.setStreet(entity.getStreet());
        address.setCity(entity.getCity());
        address.setState(entity.getState());
        address.setNeighborhood(entity.getNeighborhood());
        address.setZipCode(entity.getZipCode());
        address.setCountry(entity.getCountry());
        address.setNumber(entity.getNumber());
        return address;
    }

    public AddressEntity toEntity(Address model) {
        if (model == null) {
            return null;
        }

        AddressEntity entity = new AddressEntity();
        entity.setId(model.getId());
        entity.setStreet(model.getStreet());
        entity.setCity(model.getCity());
        entity.setState(model.getState());
        entity.setNeighborhood(model.getNeighborhood());
        entity.setZipCode(model.getZipCode());
        entity.setCountry(model.getCountry());
        entity.setNumber(model.getNumber());
        return entity;
    }
}