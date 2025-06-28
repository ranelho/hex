package com.rlti.hex.adapters.mapper;

import com.rlti.hex.adapters.output.entity.FisicaEntity;
import com.rlti.hex.adapters.output.entity.PersonEntity;
import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.core.domain.Person;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {AddressMapper.class, ContactMapper.class, DependentMapper.class})
public interface PersonMapper {
    
    @Mapping(target = "addresses.person", ignore = true)
    Person toModel(PersonEntity entity);
    
    @Mapping(target = "addresses.person", ignore = true)
    PersonEntity toEntity(Person model);
    
    @Mapping(target = "addresses.person", ignore = true)
    @Mapping(target = "contacts.fisica", ignore = true)
    @Mapping(target = "dependents.fisica", ignore = true)
    Fisica toModel(FisicaEntity entity);
    
    @Mapping(target = "addresses.person", ignore = true)
    @Mapping(target = "contacts.fisica", ignore = true)
    @Mapping(target = "dependents.fisica", ignore = true)
    FisicaEntity toEntity(Fisica model);
    
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