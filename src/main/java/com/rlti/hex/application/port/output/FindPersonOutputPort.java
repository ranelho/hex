package com.rlti.hex.application.port.output;

import com.rlti.hex.adapters.input.api.response.MonthlyStats;
import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.core.domain.Person;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface FindPersonOutputPort {
    Optional<Fisica> find(Long id);

    Page<Fisica> findAll(String name, String cpf, int page, int size);

    Optional<Person> findPerson(Long id);

    boolean exists(@CPF(message = "Invalid CPF") String cpf);

    List<Fisica> findRecent(int limit);

    List<MonthlyStats> getPersonsByMonth();
}
