package com.rlti.hex.adapters.output.repository;

import com.rlti.hex.adapters.output.entity.DependentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DependentJpaRepository extends JpaRepository<DependentEntity, Long>  {
    List<DependentEntity> findAllByFisica_Id(Long id);
}
