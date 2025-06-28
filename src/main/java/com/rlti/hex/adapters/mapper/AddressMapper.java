package com.rlti.hex.adapters.mapper;

import com.rlti.hex.adapters.output.entity.AddressEntity;
import com.rlti.hex.application.core.domain.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {
    
    @Mapping(target = "person", ignore = true)
    Address toModel(AddressEntity entity);
    
    @Mapping(target = "person", ignore = true)
    AddressEntity toEntity(Address model);
}