package com.rlti.hex.adapters.mapper;

import com.rlti.hex.adapters.output.entity.FisicaEntity;
import com.rlti.hex.adapters.output.entity.PersonEntity;
import com.rlti.hex.application.core.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
// Stream.toList() não requer importação especial no Java 21

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

        // Addresses são tratados separadamente para evitar referência circular
        return person;
    }

    public PersonEntity toEntity(Person model) {
        if (model == null) {
            return null;
        }

        PersonEntity entity = new PersonEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());

        // Mapear addresses se existirem
        if (model.getAddresses() != null) {
            entity.setAddresses(model.getAddresses().stream()
                    .map(address -> {
                        var addressEntity = addressMapper.toEntity(address);
                        addressEntity.setPerson(entity); // Definindo a referência circular
                        return addressEntity;
                    })
                    .toList());
        }

        return entity;
    }

    // Método modificado para usar com Person genérica, mas não com Fisica que tem seu próprio gerenciamento
    public void mapAddresses(Person target, PersonEntity source) {
        if (source.getAddresses() != null && !(target instanceof Fisica)) {
            target.setAddresses(source.getAddresses().stream()
                    .map(addressEntity -> {
                        Address address = addressMapper.toModel(addressEntity);
                        address.setPerson(target); // Definindo a referência circular aqui
                        return address;
                    })
                    .toList());
        }
    }

    public Fisica toModel(FisicaEntity entity) {
        if (entity == null) {
            return null;
        }

        // Usar o builder de Fisica já que a classe Fisica é imutável e utiliza Builder pattern
        Fisica fisica = Fisica.builder()
                .name(entity.getName())
                .cpf(entity.getCpf())
                .birthDate(entity.getBirthDate())
                .nameMother(entity.getNameMother())
                .nameFather(entity.getNameFather())
                .build();

        // Definir o ID manualmente
        fisica.setId(entity.getId());

        // Como a classe Fisica gerencia suas próprias relações, precisamos usar o método de atualização
        List<Contact> contacts = null;
        if (entity.getContacts() != null) {
            contacts = entity.getContacts().stream()
                    .map(contactMapper::toModel)
                    .toList();
            // As referências circulares serão configuradas pelo método setupRelationships() dentro da classe Fisica
        }

        List<Dependent> dependents = null;
        if (entity.getDependents() != null) {
            dependents = entity.getDependents().stream()
                    .map(dependentMapper::toModel)
                    .toList();
            // As referências circulares serão configuradas pelo método setupRelationships() dentro da classe Fisica
        }

        List<Address> addresses = null;
        if (entity.getAddresses() != null) {
            addresses = entity.getAddresses().stream()
                    .map(addressMapper::toModel)
                    .toList();
            // As referências circulares serão configuradas pelo método setupRelationships() dentro da classe Fisica
        }

        // Reconstruir a Fisica com as coleções completas
        fisica = Fisica.builder()
                .name(entity.getName())
                .cpf(entity.getCpf())
                .birthDate(entity.getBirthDate())
                .nameMother(entity.getNameMother())
                .nameFather(entity.getNameFather())
                .addresses(addresses)
                .contacts(contacts)
                .dependents(dependents)
                .build();

        // Definir o ID manualmente
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
        entity.setBirthDate(model.getBirthDate());
        entity.setNameMother(model.getNameMother());
        entity.setNameFather(model.getNameFather());

            entity.setContacts(model.getContacts().stream()
                .map(contact -> {
                    var contactEntity = contactMapper.toEntity(contact);
                    contactEntity.setFisica(entity); // Definindo a referência circular
                    return contactEntity;
                })
                .toList());

        // Mapeando dependentes manualmente
        if (model.getDependents() != null) {
            entity.setDependents(model.getDependents().stream()
                    .map(dependent -> {
                        var dependentEntity = dependentMapper.toEntity(dependent);
                        dependentEntity.setFisica(entity); // Definindo a referência circular
                        return dependentEntity;
                    })
                    .toList());
        }

        // Mapeando addresses
        if (model.getAddresses() != null) {
            entity.setAddresses(model.getAddresses().stream()
                    .map(address -> {
                        var addressEntity = addressMapper.toEntity(address);
                        addressEntity.setPerson(entity); // Definindo a referência circular
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

        // Mapear addresses se existirem
        if (person.getAddresses() != null) {
            entity.setAddresses(person.getAddresses().stream()
                    .map(address -> {
                        var addressEntity = addressMapper.toEntity(address);
                        addressEntity.setPerson(entity); // Definindo a referência circular
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

            // Mapear addresses se existirem
            if (model.getAddresses() != null) {
                fisicaEntity.setAddresses(model.getAddresses().stream()
                        .map(address -> {
                            var addressEntity = addressMapper.toEntity(address);
                            addressEntity.setPerson(fisicaEntity); // Definindo a referência circular
                            return addressEntity;
                        })
                        .toList());
            }

            return fisicaEntity;
        }
    }

    public void setFisicaId(Fisica target, FisicaEntity source) {
        if (source != null && source.getId() != null && target != null) {
            // Como Fisica é imutável exceto por setId, apenas definimos o ID
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