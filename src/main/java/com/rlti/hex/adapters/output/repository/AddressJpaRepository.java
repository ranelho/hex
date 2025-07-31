package com.rlti.hex.adapters.output.repository;

import com.rlti.hex.adapters.output.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressJpaRepository extends JpaRepository<AddressEntity, Long>  {
    List<AddressEntity> findAllByPerson_Id(Long idPerson);

    @Query(value = """
                SELECT p.city, COUNT(*) AS count
                FROM address p
                GROUP BY p.city
                ORDER BY count DESC
                LIMIT 5
            """, nativeQuery = true)
    List<Object[]> countByCity();
}
