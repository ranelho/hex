package com.rlti.hex.adapters.input.api;

import com.rlti.hex.adapters.input.api.response.DashboardStats;
import com.rlti.hex.adapters.input.api.response.MonthlyStats;
import com.rlti.hex.adapters.input.api.response.PersonResponse;
import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.port.input.DashboardInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardInputPort dashboardInputPort;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStats> getStats() {
        var stats = dashboardInputPort.getStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/recent-persons")
    public ResponseEntity<List<PersonResponse>> getRecentPersons(
            @RequestParam(defaultValue = "5") int limit) {
        List<Fisica> recentPersons = dashboardInputPort.findRecent(limit);

        return ResponseEntity.ok(PersonResponse.convertList(recentPersons));
    }

    @GetMapping("/persons-by-month")
    public ResponseEntity<List<MonthlyStats>> getPersonsByMonth() {
        List<MonthlyStats> monthlyStats = dashboardInputPort.getPersonsByMonth();
        return ResponseEntity.ok(monthlyStats);
    }
}
