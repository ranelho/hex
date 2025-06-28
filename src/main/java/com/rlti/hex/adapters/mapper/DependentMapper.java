package com.rlti.hex.adapters.mapper;

import com.rlti.hex.adapters.output.entity.DependentEntity;
import com.rlti.hex.application.core.domain.Dependent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DependentMapper {
    
    @Mapping(target = "fisica", ignore = true)
    Dependent toModel(DependentEntity entity);
    
    @Mapping(target = "fisica", ignore = true)
    DependentEntity toEntity(Dependent model);
}