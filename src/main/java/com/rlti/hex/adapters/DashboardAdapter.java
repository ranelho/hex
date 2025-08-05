package com.rlti.hex.adapters;

import com.rlti.hex.adapters.input.api.response.CityStats;
import com.rlti.hex.adapters.input.api.response.MonthlyStats;
import com.rlti.hex.adapters.output.repository.*;
import com.rlti.hex.application.core.domain.constants.ApplicationConstants;
import com.rlti.hex.application.port.output.DashboardOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DashboardAdapter implements DashboardOutputPort {

    private final FisicaJpaRepository fisicaJpaRepository;
    private final PersonJpaRepository personJpaRepository;
    private final AddressJpaRepository addressRepository;
    private final ContactJpaRepository contactRepository;
    private final DependentJpaRepository dependentRepository;

    @Override
    public long countPersons() {
        return fisicaJpaRepository.count();
    }

    @Override
    public long countAddresses() {
        return addressRepository.count();
    }

    @Override
    public long countContacts() {
        return contactRepository.count();
    }

    @Override
    public long countDependents() {
        return dependentRepository.count();
    }

    @Override
    public long countRecentPersons() {
        return fisicaJpaRepository.countByCreatedAtAfter(LocalDate.now().minusDays(ApplicationConstants.Dashboard.DEFAULT_RECENT_DAYS));
    }

    @Override
    public double calculateAverageAge() {
        return fisicaJpaRepository.calculateAverageAge();
    }

    @Override
    public List<MonthlyStats> getPersonsByMonth() {
        return personJpaRepository.countFisicasByMonthRaw().stream()
                .map(row -> new MonthlyStats((String) row[0], ((Number) row[1]).longValue()))
                .toList();
    }

    @Override
    public List<CityStats> getTopCities() {
        return addressRepository.countByCity().stream()
                .map(row -> new CityStats((String) row[0], ((Number) row[1]).longValue()))
                .toList();
    }
}
