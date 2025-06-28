package com.rlti.hex.adapters.mapper;

import com.rlti.hex.adapters.output.entity.FisicaEntity;
import com.rlti.hex.adapters.output.entity.PersonEntity;
import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.core.domain.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

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
    
    // Método específico para converter Person para FisicaEntity
    default FisicaEntity toFisicaEntity(Person model) {
        if (model == null) {
            return null;
        }
        
        // Se o model já é uma instância de Fisica, podemos fazer uma conversão mais completa
        if (model instanceof Fisica fisica) {
            return toEntity(fisica);
        } else {
            // Conversão básica se for apenas Person
            FisicaEntity fisicaEntity = new FisicaEntity();
            fisicaEntity.setId(model.getId());
            fisicaEntity.setName(model.getName());
            return fisicaEntity;
        }
    }
}