package com.rlti.hex.adapters.mapper;

import com.rlti.hex.adapters.output.entity.AddressEntity;
import com.rlti.hex.adapters.output.entity.ContactEntity;
import com.rlti.hex.adapters.output.entity.DependentEntity;
import com.rlti.hex.adapters.output.entity.FisicaEntity;
import com.rlti.hex.adapters.output.entity.PersonEntity;
import com.rlti.hex.application.core.domain.Address;
import com.rlti.hex.application.core.domain.Contact;
import com.rlti.hex.application.core.domain.Dependent;
import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.core.domain.Person;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockMappers {
    
    @Bean
    public PersonMapper personMapper() {
        return new PersonMapperImpl();
    }
    
    @Bean
    public AddressMapper addressMapper() {
        return new AddressMapperImpl();
    }
    
    @Bean
    public ContactMapper contactMapper() {
        return new ContactMapperImpl();
    }
    
    @Bean
    public DependentMapper dependentMapper() {
        return new DependentMapperImpl();
    }
    
    // Classes simplificadas para testes
    public static class PersonMapperImpl implements PersonMapper {
        @Override
        public Person toModel(PersonEntity entity) {
            return new Person();
        }
        
        @Override
        public PersonEntity toEntity(Person model) {
            return new PersonEntity();
        }
        
        @Override
        public Fisica toModel(FisicaEntity entity) {
            return new Fisica();
        }
        
        @Override
        public FisicaEntity toEntity(Fisica model) {
            return new FisicaEntity();
        }
        
        @Override
        public FisicaEntity toFisicaEntity(Person model) {
            return new FisicaEntity();
        }
    }
    
    public static class AddressMapperImpl implements AddressMapper {
        @Override
        public Address toModel(AddressEntity entity) {
            return new Address();
        }
        
        @Override
        public AddressEntity toEntity(Address model) {
            return new AddressEntity();
        }
    }
    
    public static class ContactMapperImpl implements ContactMapper {
        @Override
        public Contact toModel(ContactEntity entity) {
            return new Contact();
        }
        
        @Override
        public ContactEntity toEntity(Contact model) {
            return new ContactEntity();
        }
    }
    
    public static class DependentMapperImpl implements DependentMapper {
        @Override
        public Dependent toModel(DependentEntity entity) {
            return new Dependent();
        }
        
        @Override
        public DependentEntity toEntity(Dependent model) {
            return new DependentEntity();
        }
    }
}