package com.rlti.hex.adapters.output.repository;

import com.rlti.hex.adapters.output.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonJpaRepository extends JpaRepository<PersonEntity, Long> {
    @Query(value = """
                SELECT TO_CHAR(p.created_at, 'YYYY-MM') AS month, COUNT(*) AS count
                FROM person  p
                GROUP BY TO_CHAR(p.created_at, 'YYYY-MM')
                ORDER BY month DESC
            """, nativeQuery = true)
    List<Object[]> countFisicasByMonthRaw();
}
