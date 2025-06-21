package com.rlti.hex.adapters;

import com.rlti.hex.adapters.output.entity.DependentEntity;
import com.rlti.hex.adapters.output.repository.DependentJpaRepository;
import com.rlti.hex.application.core.domain.Dependent;
import com.rlti.hex.application.core.domain.Person;
import com.rlti.hex.application.port.output.DeleteDependentOutputPort;
import com.rlti.hex.application.port.output.FindDependentOutputPort;
import com.rlti.hex.application.port.output.InsertDependentToPersonOutputPort;
import com.rlti.hex.application.port.output.UpdateDependentOutputPort;
import com.rlti.hex.config.aspect.Monitored;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Monitored
public class DependentAdapter implements InsertDependentToPersonOutputPort,
        FindDependentOutputPort, DeleteDependentOutputPort, UpdateDependentOutputPort {

    private final DependentJpaRepository dependentJpaRepository;
    private final ModelMapper modelMapper;

    @Override
    public void delete(Dependent dependent) {
        var dependentEntity = modelMapper.map(dependent, DependentEntity.class);
        dependentJpaRepository.delete(dependentEntity);
    }

    @Override
    public Optional<Dependent> find(Long id) {
        return dependentJpaRepository.findById(id)
                .map(dependentEntity -> modelMapper.map(dependentEntity, Dependent.class));
    }

    @Override
    public List<Dependent> findAllByPerson(Person person) {
        var dependents = dependentJpaRepository.findAllByFisica_Id(person.getId());
        return dependents.stream()
                .map(dependentEntity -> modelMapper.map(dependentEntity, Dependent.class))
                .toList();
    }

    @Override
    public Dependent insert(Dependent dependent) {
        var dependentEntity = modelMapper.map(dependent, DependentEntity.class);
        var savedDependentEntity = dependentJpaRepository.save(dependentEntity);
        return modelMapper.map(savedDependentEntity, Dependent.class);
    }

    @Override
    public Dependent update(Dependent dependent) {
        return insert(dependent);
    }
}