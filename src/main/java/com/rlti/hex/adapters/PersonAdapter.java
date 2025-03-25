package com.rlti.hex.adapters;

import com.rlti.hex.adapters.output.entity.FisicaEntity;
import com.rlti.hex.adapters.output.repository.FisicaJpaRepository;
import com.rlti.hex.adapters.output.repository.PersonJpaRepository;
import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.core.domain.Person;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import com.rlti.hex.application.port.output.InsertPersonOutputPort;
import com.rlti.hex.application.port.output.UpdatePersonOutputPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonAdapter implements InsertPersonOutputPort, FindPersonOutputPort, UpdatePersonOutputPort {

    private final FisicaJpaRepository fisicaJpaRepository;
    private final PersonJpaRepository personJpaRepository;
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

    @Override
    public Page<Fisica> findAll(Pageable pageable) {
        var fisicaPage = fisicaJpaRepository.findAll(pageable);
        return fisicaPage.map(entity -> modelMapper.map(entity, Fisica.class));
    }

    @Override
    public Optional<Person> findPerson(Long id) {
        return personJpaRepository.findById(id)
                .map(entity -> modelMapper.map(entity, Person.class));
    }

    @Override
    public Fisica update(Fisica person) {
        var fisicaEntity = modelMapper.map(person, FisicaEntity.class);
        var updatedFisica = fisicaJpaRepository.save(fisicaEntity);
        return modelMapper.map(updatedFisica, Fisica.class);
    }
}
