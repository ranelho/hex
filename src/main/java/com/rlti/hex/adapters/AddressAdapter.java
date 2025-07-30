package com.rlti.hex.adapters;

import com.rlti.hex.adapters.mapper.AddressMapper;
import com.rlti.hex.adapters.output.entity.PersonEntity;
import com.rlti.hex.adapters.output.repository.AddressJpaRepository;
import com.rlti.hex.application.core.domain.Address;
import com.rlti.hex.application.port.output.DeleteAddressOutputPort;
import com.rlti.hex.application.port.output.FindAddressOutputPort;
import com.rlti.hex.application.port.output.InsertAddressToPersonOutputPort;
import com.rlti.hex.application.port.output.UpdateAddressOutputPort;
import com.rlti.hex.config.aspect.Monitored;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Monitored
public class AddressAdapter implements InsertAddressToPersonOutputPort,
        FindAddressOutputPort, UpdateAddressOutputPort, DeleteAddressOutputPort {

    private final AddressJpaRepository addressJpaRepository;
    private final AddressMapper addressMapper;

    @Transactional
    @Override
    public Address insert(Address address) {
        // Convertemos para entidade usando o mapper
        var addressEntity = addressMapper.toEntity(address);

        // Configuramos manualmente a associação com Person
        if (address.getPerson() != null && address.getPerson().getId() != null) {
            PersonEntity personEntity = new PersonEntity();
            personEntity.setId(address.getPerson().getId());
            addressEntity.setPerson(personEntity);
        }

        // Salvamos no banco
        var addressEntitySaved = addressJpaRepository.save(addressEntity);

        // Convertemos de volta para o modelo de domínio usando o mapper
        Address result = addressMapper.toModel(addressEntitySaved);

        // Mantemos a associação com Person do objeto original
        result.setPerson(address.getPerson());

        return result;
    }

    @Override
    public Optional<Address> find(Long id) {
        return addressJpaRepository.findById(id)
                .map(addressMapper::toModel);
    }

    @Override
    public List<Address> findAll(Long idPerson) {
        var addressEntityList = addressJpaRepository.findAllByPerson_Id(idPerson);
        return addressEntityList.stream()
                .map(addressMapper::toModel)
                .toList();
    }

    @Override
    public Address update(Address address) {
        return insert(address);
    }

    @Override
    public void delete(Address address) {
        addressJpaRepository.delete(addressMapper.toEntity(address));
    }
}