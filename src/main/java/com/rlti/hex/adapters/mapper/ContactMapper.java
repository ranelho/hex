package com.rlti.hex.adapters.mapper;

import com.rlti.hex.adapters.output.entity.ContactEntity;
import com.rlti.hex.application.core.domain.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContactMapper {
    
    @Mapping(target = "fisica", ignore = true)
    Contact toModel(ContactEntity entity);
    
    @Mapping(target = "fisica", ignore = true)
    ContactEntity toEntity(Contact model);
}