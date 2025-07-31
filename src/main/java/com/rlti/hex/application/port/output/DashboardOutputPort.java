package com.rlti.hex.application.port.output;

import com.rlti.hex.adapters.input.api.response.CityStats;
import com.rlti.hex.adapters.input.api.response.MonthlyStats;

import java.util.List;

public interface DashboardOutputPort {
    long countPersons();

    long countAddresses();

    long countContacts();

    long countDependents();

    long countRecentPersons();

    double calculateAverageAge();

    List<MonthlyStats> getPersonsByMonth();

    List<CityStats> getTopCities();
}
