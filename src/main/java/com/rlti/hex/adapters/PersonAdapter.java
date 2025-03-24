package com.rlti.hex.adapters;

import com.rlti.hex.adapters.output.entity.FisicaEntity;
import com.rlti.hex.adapters.output.repository.FisicaJpaRepository;
import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.core.domain.Person;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import com.rlti.hex.application.port.output.InsertPersonOutputPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonAdapter implements InsertPersonOutputPort, FindPersonOutputPort {

    private final FisicaJpaRepository fisicaJpaRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Fisica insert(Person person) {
        var fisicaEntity = modelMapper.map(person, FisicaEntity.class);
        var savedFisica = fisicaJpaRepository.save(fisicaEntity);
        return modelMapper.map(savedFisica, Fisica.class);
    }

    @Override
    public Optional<Fisica> find(Long id) {
        return fisicaJpaRepository.findById(id)
                .map(entity -> modelMapper.map(entity, Fisica.class));
    }
}
