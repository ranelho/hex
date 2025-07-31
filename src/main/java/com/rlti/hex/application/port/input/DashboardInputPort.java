package com.rlti.hex.application.port.input;

import com.rlti.hex.adapters.input.api.response.DashboardStats;
import com.rlti.hex.adapters.input.api.response.MonthlyStats;
import com.rlti.hex.application.core.domain.Fisica;

import java.util.List;

public interface DashboardInputPort {
    List<MonthlyStats> getPersonsByMonth();

    List<Fisica> findRecent(int limit);

    DashboardStats getStats();
}
