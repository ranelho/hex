package com.rlti.hex.adapters;

import com.rlti.hex.adapters.output.entity.FisicaEntity;
import com.rlti.hex.adapters.output.repository.FisicaJpaRepository;
import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.core.domain.Person;
import com.rlti.hex.application.port.output.InsertPersonOutputPort;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonAdapter implements InsertPersonOutputPort {

    private final FisicaJpaRepository fisicaJpaRepository;
    private final ModelMapper modelMapper;

    @Override
    public Fisica insert(Person person) {
        var fisicaEntity = modelMapper.map(person, FisicaEntity.class);
        var savedFisica = fisicaJpaRepository.save(fisicaEntity);
        return modelMapper.map(savedFisica, Fisica.class);
    }
}
