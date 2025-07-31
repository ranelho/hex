package com.rlti.hex.adapters.output.repository;

import com.rlti.hex.adapters.output.entity.FisicaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface FisicaJpaRepository extends JpaRepository<FisicaEntity, Long> {
    boolean existsByCpf(String cpf);

    Page<FisicaEntity> findByNameContainingIgnoreCase(String nome, Pageable pageable);

    Page<FisicaEntity> findByCpfContaining(String cpf, Pageable pageable);

    Page<FisicaEntity> findByNameContainingIgnoreCaseAndCpfContaining(String nome, String cpf, Pageable pageable);

    Page<FisicaEntity> findAllByOrderByIdDesc(Pageable pageable);

    @Query(value = "SELECT ROUND(AVG(EXTRACT(YEAR FROM AGE(p.birth_date)))::numeric, 1) AS average_age FROM person_fisica p", nativeQuery = true)
    Double calculateAverageAge();

    long countByCreatedAtAfter(LocalDate date);
}
