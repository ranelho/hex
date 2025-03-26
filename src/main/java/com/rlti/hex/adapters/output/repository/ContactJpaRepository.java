package com.rlti.hex.adapters.output.repository;

import com.rlti.hex.adapters.output.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactJpaRepository extends JpaRepository<ContactEntity, Long> {
    List<ContactEntity> findAllByFisica_Id(Long id);
}
