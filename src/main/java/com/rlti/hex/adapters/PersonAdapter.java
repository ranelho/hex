package com.rlti.hex.adapters;

import com.rlti.hex.adapters.input.api.response.MonthlyStats;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonAdapter implements InsertPersonOutputPort, FindPersonOutputPort,
        UpdatePersonOutputPort, DeletePersonOutputPort {

    private final FisicaJpaRepository fisicaJpaRepository;
    private final PersonJpaRepository personJpaRepository;
    private final PersonMapper personMapper;

    @Transactional
    @CacheEvict(value = "persons", allEntries = true)
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

    @Cacheable(value = "persons", key = "'findAll:' + #name + ':' + #cpf + ':' + #page + ':' + #size")
    @Override
    public Page<Fisica> findAll(String name, String cpf, int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);

        if (name != null && cpf != null) {
            return fisicaJpaRepository.findByNameContainingIgnoreCaseAndCpfContaining(name, cpf, pageable)
                    .map(personMapper::toModel);
        } else if (name != null) {
            return fisicaJpaRepository.findByNameContainingIgnoreCase(name, pageable)
                    .map(personMapper::toModel);
        } else if (cpf != null) {
            return fisicaJpaRepository.findByCpfContaining(cpf, pageable)
                    .map(personMapper::toModel);
        }

        return fisicaJpaRepository.findAll(pageable)
                .map(personMapper::toModel);
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

    @Cacheable(value = "persons", key = "'recent:' + #limit")
    @Override
    public List<Fisica> findRecent(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        var recentPeople = fisicaJpaRepository
                .findAllByOrderByIdDesc(pageable)
                .getContent();

        return recentPeople.stream()
                .map(personMapper::toModel)
                .toList();
    }

    @Transactional
    @CacheEvict(value = "persons", allEntries = true)
    @Override
    public Fisica update(Fisica person) {
        var fisicaEntity = personMapper.toEntity(person);
        var updatedFisica = fisicaJpaRepository.save(fisicaEntity);
        return personMapper.toModel(updatedFisica);
    }

    @Transactional
    @CacheEvict(value = "persons", allEntries = true)
    @Override
    public void delete(Fisica person) {
        personJpaRepository.deleteById(person.getId());
    }

    @Cacheable(value = "persons", key = "'byMonth'")
    @Override
    public List<MonthlyStats> getPersonsByMonth() {
        List<Object[]> rawResult = personJpaRepository.countFisicasByMonthRaw();

        return rawResult.stream()
                .map(row -> new MonthlyStats(
                        (String) row[0],
                        ((Number) row[1]).longValue()
                ))
                .toList();
    }
}
