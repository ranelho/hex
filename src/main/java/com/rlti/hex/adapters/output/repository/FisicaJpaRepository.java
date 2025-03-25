package com.rlti.hex.adapters.output.repository;

import com.rlti.hex.adapters.output.entity.FisicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FisicaJpaRepository extends JpaRepository<FisicaEntity, Long> {
    boolean existsByCpf(String cpf);
}
