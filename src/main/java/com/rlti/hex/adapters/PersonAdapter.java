package com.rlti.hex.adapters;

import com.rlti.hex.adapters.mapper.PersonMapper;
import com.rlti.hex.adapters.output.repository.FisicaJpaRepository;
import com.rlti.hex.adapters.output.repository.PersonJpaRepository;
import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.core.domain.Person;
import com.rlti.hex.application.port.output.DeletePersonOutputPort;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import com.rlti.hex.application.port.output.InsertPersonOutputPort;
import com.rlti.hex.application.port.output.UpdatePersonOutputPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonAdapter implements InsertPersonOutputPort, FindPersonOutputPort,
        UpdatePersonOutputPort, DeletePersonOutputPort {

    private final FisicaJpaRepository fisicaJpaRepository;
    private final PersonJpaRepository personJpaRepository;
    private final PersonMapper personMapper;

    @Transactional
    @Override
    public Fisica insert(Person person) {
        var fisicaEntity = personMapper.toFisicaEntity(person);
        var savedFisica = fisicaJpaRepository.save(fisicaEntity);
        return personMapper.toModel(savedFisica);
    }

    @Override
    public Optional<Fisica> find(Long id) {
        return fisicaJpaRepository.findById(id)
                .map(personMapper::toModel);
    }

    @Override
    public Page<Fisica> findAll(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        var fisicaPage = fisicaJpaRepository.findAll(pageable);
        return fisicaPage.map(personMapper::toModel);
    }

    @Override
    public Optional<Person> findPerson(Long id) {
        return personJpaRepository.findById(id)
                .map(personMapper::toModel);
    }

    @Override
    public boolean exists(String cpf) {
        return fisicaJpaRepository.existsByCpf(cpf);
    }

    @Override
    public Fisica update(Fisica person) {
        var fisicaEntity = personMapper.toEntity(person);
        var updatedFisica = fisicaJpaRepository.save(fisicaEntity);
        return personMapper.toModel(updatedFisica);
    }

    @Override
    public void delete(Fisica person) {
        personJpaRepository.deleteById(person.getId());
    }
}