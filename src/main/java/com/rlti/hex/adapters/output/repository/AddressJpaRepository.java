package com.rlti.hex.adapters.output.repository;

import com.rlti.hex.adapters.output.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressJpaRepository extends JpaRepository<AddressEntity, Long>  {
    List<AddressEntity> findAllByPerson_Id(Long idPerson);
}
