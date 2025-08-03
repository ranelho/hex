package com.rlti.hex.adapters.mapper;

import com.rlti.hex.adapters.output.entity.FisicaEntity;
import com.rlti.hex.adapters.output.entity.PersonEntity;
import com.rlti.hex.application.core.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class PersonMapper {

    private final ContactMapper contactMapper;
    private final DependentMapper dependentMapper;
    private final AddressMapper addressMapper;

    public Person toModel(PersonEntity entity) {
        if (entity == null) {
            return null;
        }

        Person person = new Person();
        person.setId(entity.getId());
        person.setName(entity.getName());

    
        return person;
    }

    public PersonEntity toEntity(Person model) {
        if (model == null) {
            return null;
        }

        PersonEntity entity = new PersonEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());


        if (model.getAddresses() != null) {
            entity.setAddresses(model.getAddresses().stream()
                    .map(address -> {
                        var addressEntity = addressMapper.toEntity(address);
                        addressEntity.setPerson(entity);
                        return addressEntity;
                    })
                    .toList());
        }

        return entity;
    }

    public void mapAddresses(Person target, PersonEntity source) {
        if (source.getAddresses() != null && !(target instanceof Fisica)) {
            target.setAddresses(source.getAddresses().stream()
                    .map(addressEntity -> {
                        Address address = addressMapper.toModel(addressEntity);
                        address.setPerson(target);
                        return address;
                    })
                    .toList());
        }
    }

    public Fisica toModel(FisicaEntity entity) {
        if (entity == null) {
            return null;
        }


        Fisica fisica = Fisica.builder()
                .name(entity.getName())
                .cpf(entity.getCpf())
                .rg(entity.getRg())
                .rgIssuer(entity.getRgIssuer())
                .birthDate(entity.getBirthDate())
                .nameMother(entity.getNameMother())
                .nameFather(entity.getNameFather())
                .maritalStatus(entity.getMaritalStatus())
                .profession(entity.getProfession())
                .nationality(entity.getNationality())
                .gender(entity.getGender())
                .emergencyContact(entity.getEmergencyContact())
                .emergencyPhone(entity.getEmergencyPhone())
                .build();


        fisica.setId(entity.getId());

        List<Contact> contacts = null;
        if (entity.getContacts() != null) {
            contacts = entity.getContacts().stream()
                    .map(contactMapper::toModel)
                    .toList();
        }

        List<Dependent> dependents = null;
        if (entity.getDependents() != null) {
            dependents = entity.getDependents().stream()
                    .map(dependentMapper::toModel)
                    .toList();
        }

        List<Address> addresses = null;
        if (entity.getAddresses() != null) {
            addresses = entity.getAddresses().stream()
                    .map(addressMapper::toModel)
                    .toList();
        }


        fisica = Fisica.builder()
                .name(entity.getName())
                .cpf(entity.getCpf())
                .rg(entity.getRg())
                .rgIssuer(entity.getRgIssuer())
                .birthDate(entity.getBirthDate())
                .nameMother(entity.getNameMother())
                .nameFather(entity.getNameFather())
                .maritalStatus(entity.getMaritalStatus())
                .profession(entity.getProfession())
                .nationality(entity.getNationality())
                .gender(entity.getGender())
                .emergencyContact(entity.getEmergencyContact())
                .emergencyPhone(entity.getEmergencyPhone())
                .addresses(addresses)
                .contacts(contacts)
                .dependents(dependents)
                .build();


        fisica.setId(entity.getId());

        return fisica;
    }

    public FisicaEntity toEntity(Fisica model) {
        if (model == null) {
            return null;
        }

        FisicaEntity entity = new FisicaEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setCpf(model.getCpf());
        entity.setRg(model.getRg());
        entity.setRgIssuer(model.getRgIssuer());
        entity.setBirthDate(model.getBirthDate());
        entity.setNameMother(model.getNameMother());
        entity.setNameFather(model.getNameFather());
        entity.setMaritalStatus(model.getMaritalStatus());
        entity.setProfession(model.getProfession());
        entity.setNationality(model.getNationality());
        entity.setGender(model.getGender());
        entity.setEmergencyContact(model.getEmergencyContact());
        entity.setEmergencyPhone(model.getEmergencyPhone());

            entity.setContacts(model.getContacts().stream()
                .map(contact -> {
                    var contactEntity = contactMapper.toEntity(contact);
                    contactEntity.setFisica(entity);
                    return contactEntity;
                })
                .toList());


        if (model.getDependents() != null) {
            entity.setDependents(model.getDependents().stream()
                    .map(dependent -> {
                        var dependentEntity = dependentMapper.toEntity(dependent);
                        dependentEntity.setFisica(entity);
                        return dependentEntity;
                    })
                    .toList());
        }


        if (model.getAddresses() != null) {
            entity.setAddresses(model.getAddresses().stream()
                    .map(address -> {
                        var addressEntity = addressMapper.toEntity(address);
                        addressEntity.setPerson(entity);
                        return addressEntity;
                    })
                    .toList());
        }

        return entity;
    }

    public PersonEntity personToPersonEntity(Person person) {
        if (person == null) {
            return null;
        }
        PersonEntity entity = new PersonEntity();
        entity.setId(person.getId());
        entity.setName(person.getName());


        if (person.getAddresses() != null) {
            entity.setAddresses(person.getAddresses().stream()
                    .map(address -> {
                        var addressEntity = addressMapper.toEntity(address);
                        addressEntity.setPerson(entity);
                        return addressEntity;
                    })
                    .toList());
        }

        return entity;
    }

    public FisicaEntity toFisicaEntity(Person model) {
        if (model == null) {
            return null;
        }

        if (model instanceof Fisica fisica) {
            return toEntity(fisica);
        } else {
            FisicaEntity fisicaEntity = new FisicaEntity();
            fisicaEntity.setId(model.getId());
            fisicaEntity.setName(model.getName());

    
            if (model.getAddresses() != null) {
                fisicaEntity.setAddresses(model.getAddresses().stream()
                        .map(address -> {
                            var addressEntity = addressMapper.toEntity(address);
                            addressEntity.setPerson(fisicaEntity);
                            return addressEntity;
                        })
                        .toList());
            }

            return fisicaEntity;
        }
    }

    public void setFisicaId(Fisica target, FisicaEntity source) {
        if (source != null && source.getId() != null && target != null) {
    
            target.setId(source.getId());
        }
    }

    public void setPersonId(Person target, PersonEntity source) {
        if (source != null && source.getId() != null) {
            target.setId(source.getId());
        }
    }

    public void setPersonEntityId(PersonEntity target, Person source) {
        if (source != null && source.getId() != null) {
            target.setId(source.getId());
        }
    }

    public void setFisicaEntityId(FisicaEntity target, Fisica source) {
        if (source != null && source.getId() != null) {
            target.setId(source.getId());
        }
    }
}