package com.rlti.hex.adapters;

import com.rlti.hex.adapters.mapper.DependentMapper;
import com.rlti.hex.adapters.output.repository.DependentJpaRepository;
import com.rlti.hex.application.core.domain.Dependent;
import com.rlti.hex.application.core.domain.Person;
import com.rlti.hex.application.port.output.DeleteDependentOutputPort;
import com.rlti.hex.application.port.output.FindDependentOutputPort;
import com.rlti.hex.application.port.output.InsertDependentToPersonOutputPort;
import com.rlti.hex.application.port.output.UpdateDependentOutputPort;
import com.rlti.hex.config.aspect.Monitored;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Monitored
public class DependentAdapter implements InsertDependentToPersonOutputPort,
        FindDependentOutputPort, DeleteDependentOutputPort, UpdateDependentOutputPort {

    private final DependentJpaRepository dependentJpaRepository;
    private final DependentMapper dependentMapper;

    @Override
    public void delete(Dependent dependent) {
        var dependentEntity = dependentMapper.toEntity(dependent);
        dependentJpaRepository.delete(dependentEntity);
    }

    @Override
    public Optional<Dependent> find(Long id) {
        return dependentJpaRepository.findById(id)
                .map(dependentMapper::toModel);
    }

    @Override
    public List<Dependent> findAllByPerson(Person person) {
        var dependents = dependentJpaRepository.findAllByFisica_Id(person.getId());
        return dependents.stream()
                .map(dependentMapper::toModel)
                .toList();
    }

    @Override
    public Dependent insert(Dependent dependent) {
        var dependentEntity = dependentMapper.toEntity(dependent);
        var savedDependentEntity = dependentJpaRepository.save(dependentEntity);
        return dependentMapper.toModel(savedDependentEntity);
    }

    @Override
    public Dependent update(Dependent dependent) {
        return insert(dependent);
    }
}