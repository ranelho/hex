package com.rlti.hex.adapters.mapper;

import com.rlti.hex.adapters.output.entity.DependentEntity;
import com.rlti.hex.application.core.domain.Dependent;
import org.springframework.stereotype.Component;

@Component
public class DependentMapper {

    public Dependent toModel(DependentEntity entity) {
        if (entity == null) {
            return null;
        }

        Dependent dependent = new Dependent();
        dependent.setId(entity.getId());
        dependent.setName(entity.getName());
        dependent.setCpf(entity.getCpf());
        dependent.setBirthDate(entity.getBirthDate());
        dependent.setDependentType(entity.getDependentType());

        return dependent;
    }

    public DependentEntity toEntity(Dependent model) {
        if (model == null) {
            return null;
        }

        DependentEntity entity = new DependentEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setCpf(model.getCpf());
        entity.setBirthDate(model.getBirthDate());
        entity.setDependentType(model.getDependentType());

        return entity;
    }
}