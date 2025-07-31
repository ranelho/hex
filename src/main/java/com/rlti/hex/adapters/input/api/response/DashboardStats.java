package com.rlti.hex.adapters.input.api.response;

import java.util.List;

public record   DashboardStats(
         Long totalPersons,
         Long totalAddresses,
         Long totalContacts,
         Long totalDependents,
         Long recentPersons,
         Double averageAge,
         List<MonthlyStats> personsByMonth,
         List<CityStats> topCities
) {
}
