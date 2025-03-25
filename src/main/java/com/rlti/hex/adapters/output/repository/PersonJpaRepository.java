package com.rlti.hex.adapters.output.repository;

import com.rlti.hex.adapters.output.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonJpaRepository extends JpaRepository<PersonEntity, Long>  {
}
