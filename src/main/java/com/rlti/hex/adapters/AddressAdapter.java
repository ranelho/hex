package com.rlti.hex.adapters;

import com.rlti.hex.adapters.output.entity.AddressEntity;
import com.rlti.hex.adapters.output.repository.AddressJpaRepository;
import com.rlti.hex.application.core.domain.Address;
import com.rlti.hex.application.port.output.DeleteAddressOutputPort;
import com.rlti.hex.application.port.output.FindAddressOutputPort;
import com.rlti.hex.application.port.output.InsertAddressToPersonOutputPort;
import com.rlti.hex.application.port.output.UpdateAddressOutputPort;
import com.rlti.hex.config.aspect.Monitored;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Monitored
public class AddressAdapter implements InsertAddressToPersonOutputPort,
        FindAddressOutputPort, UpdateAddressOutputPort, DeleteAddressOutputPort {

    private final AddressJpaRepository addressJpaRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Address insert(Address address) {
        var addressEntity = modelMapper.map(address, AddressEntity.class);
        var addressEntitySaved = addressJpaRepository.save(addressEntity);
        return modelMapper.map(addressEntitySaved, Address.class);
    }

    @Override
    public Optional<Address> find(Long id) {
        return addressJpaRepository.findById(id)
                .map(addressEntity -> modelMapper.map(addressEntity, Address.class));
    }

    @Override
    public List<Address> findAll(Long idPerson) {
        var addressEntityList = addressJpaRepository.findAllByPerson_Id(idPerson);
        return addressEntityList.stream()
                .map(addressEntity -> modelMapper.map(addressEntity, Address.class))
                .toList();
    }

    @Override
    public Address update(Address address) {
        return insert(address);
    }

    @Override
    public void delete(Address address) {
        addressJpaRepository.delete(modelMapper.map(address, AddressEntity.class));
    }
}
