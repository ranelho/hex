package com.rlti.hex.adapters.output.repository;

import com.rlti.hex.adapters.output.entity.FisicaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FisicaJpaRepository extends JpaRepository<FisicaEntity, Long> {
    boolean existsByCpf(String cpf);
    Page<FisicaEntity> findByNameContainingIgnoreCase(String nome, Pageable pageable);
    Page<FisicaEntity> findByCpfContaining(String cpf, Pageable pageable);
    Page<FisicaEntity> findByNameContainingIgnoreCaseAndCpfContaining(String nome, String cpf, Pageable pageable);
}
