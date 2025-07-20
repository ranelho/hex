package com.rlti.hex.adapters.mapper;

import com.rlti.hex.adapters.output.entity.FisicaEntity;
import com.rlti.hex.adapters.output.entity.PersonEntity;
import com.rlti.hex.application.core.domain.Address;
import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.core.domain.Person;
import org.mapstruct.*;
import org.mapstruct.Named;

import java.util.stream.Collectors;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                ContactMapper.class, DependentMapper.class
        }
)
public interface PersonMapper {

    @Mapping(target = "addresses", ignore = true)
    Person toModel(PersonEntity entity);

    @Mapping(target = "addresses", ignore = true)
    PersonEntity toEntity(Person model);

    @AfterMapping
    default void mapAddresses(@MappingTarget Person target, PersonEntity source) {
        if (source.getAddresses() != null) {
            target.setAddresses(source.getAddresses().stream()
                .map(addressEntity -> {
                    Address address = new Address();
                    address.setId(addressEntity.getId());
                    address.setStreet(addressEntity.getStreet());
                    address.setCity(addressEntity.getCity());
                    address.setState(addressEntity.getState());
                    address.setNeighborhood(addressEntity.getNeighborhood());
                    address.setZipCode(addressEntity.getZipCode());
                    address.setCountry(addressEntity.getCountry());
                    address.setNumber(addressEntity.getNumber());
                    address.setPerson(target); // Definindo a referência circular aqui
                    return address;
                })
                .collect(Collectors.toList()));
        }
    }

    @Mapping(target = "addresses.person", ignore = true)
    @Mapping(target = "contacts.fisica", ignore = true)
    @Mapping(target = "dependents.fisica", ignore = true)
    Fisica toModel(FisicaEntity entity);

    @Mapping(target = "addresses.person", ignore = true)
    @Mapping(target = "contacts.fisica", ignore = true)
    @Mapping(target = "dependents.fisica", ignore = true)
    FisicaEntity toEntity(Fisica model);

    // Método qualificado para conversões de Person para PersonEntity
    @Named("toPerson")
    default PersonEntity personToPersonEntity(Person person) {
        if (person == null) {
            return null;
        }
        PersonEntity entity = new PersonEntity();
        entity.setId(person.getId());
        entity.setName(person.getName());
        return entity;
    }

    default FisicaEntity toFisicaEntity(Person model) {
        if (model == null) {
            return null;
        }

        if (model instanceof Fisica fisica) {
            return toEntity(fisica);
        } else {
            FisicaEntity fisicaEntity = new FisicaEntity();
            fisicaEntity.setId(model.getId());
            fisicaEntity.setName(model.getName());
            return fisicaEntity;
        }
    }

    @AfterMapping
    default void setFisicaId(@MappingTarget Fisica target, FisicaEntity source) {
        if (source != null && source.getId() != null) {
            target.setId(source.getId());
        }
    }

    @AfterMapping
    default void setPersonId(@MappingTarget Person target, PersonEntity source) {
        if (source != null && source.getId() != null) {
            target.setId(source.getId());
        }
    }

    @AfterMapping
    default void setPersonEntityId(@MappingTarget PersonEntity target, Person source) {
        if (source != null && source.getId() != null) {
            target.setId(source.getId());
        }
    }

    @AfterMapping
    default void setFisicaEntityId(@MappingTarget FisicaEntity target, Fisica source) {
        if (source != null && source.getId() != null) {
            target.setId(source.getId());
        }
    }
}