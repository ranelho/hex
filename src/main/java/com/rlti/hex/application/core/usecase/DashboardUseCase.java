package com.rlti.hex.application.core.usecase;

import com.rlti.hex.adapters.input.api.response.DashboardStats;
import com.rlti.hex.adapters.input.api.response.MonthlyStats;
import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.DashboardInputPort;
import com.rlti.hex.application.port.output.DashboardOutputPort;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import com.rlti.hex.config.aspect.Monitored;

import java.util.List;

@UseCase
@Monitored
public class DashboardUseCase implements DashboardInputPort {

    private final FindPersonOutputPort findPersonOutputPort;
    private final DashboardOutputPort dashboardOutputPort;

    public DashboardUseCase(
            FindPersonOutputPort findPersonOutputPort,
            DashboardOutputPort dashboardOutputPort
    ) {
        this.findPersonOutputPort = findPersonOutputPort;
        this.dashboardOutputPort = dashboardOutputPort;
    }

    @Override
    public List<MonthlyStats> getPersonsByMonth() {
        return findPersonOutputPort.getPersonsByMonth();
    }

    @Override
    public List<Fisica> findRecent(int limit) {
        return findPersonOutputPort.findRecent(limit);
    }

    @Override
    public DashboardStats getStats() {
        return new DashboardStats(
                dashboardOutputPort.countPersons(),
                dashboardOutputPort.countAddresses(),
                dashboardOutputPort.countContacts(),
                dashboardOutputPort.countDependents(),
                dashboardOutputPort.countRecentPersons(),
                dashboardOutputPort.calculateAverageAge(),
                dashboardOutputPort.getPersonsByMonth(),
                dashboardOutputPort.getTopCities()
        );
    }
}
