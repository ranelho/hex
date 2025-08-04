package com.rlti.hex.application.core.usecase;

import com.rlti.hex.adapters.input.api.response.DashboardStats;
import com.rlti.hex.adapters.input.api.response.MonthlyStats;
import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.port.output.DashboardOutputPort;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("DashboardUseCase Unit Tests")
class DashboardUseCaseUnitTest {

    @Mock
    private FindPersonOutputPort findPersonOutputPort;

    @Mock
    private DashboardOutputPort dashboardOutputPort;

    private DashboardUseCase dashboardUseCase;

    @BeforeEach
    void setUp() {
        dashboardUseCase = new DashboardUseCase(findPersonOutputPort, dashboardOutputPort);
    }

    @Test
    @DisplayName("Should return dashboard statistics")
    void shouldReturnDashboardStatistics() {
        // Given
        when(dashboardOutputPort.countPersons()).thenReturn(10L);
        when(dashboardOutputPort.countAddresses()).thenReturn(15L);
        when(dashboardOutputPort.countContacts()).thenReturn(20L);
        when(dashboardOutputPort.countDependents()).thenReturn(5L);
        when(dashboardOutputPort.countRecentPersons()).thenReturn(3L);
        when(dashboardOutputPort.calculateAverageAge()).thenReturn(35.5);
        when(dashboardOutputPort.getPersonsByMonth()).thenReturn(Arrays.asList());
        when(dashboardOutputPort.getTopCities()).thenReturn(Arrays.asList());

        // When
        DashboardStats stats = dashboardUseCase.getStats();

        // Then
        assertThat(stats).isNotNull();
        assertThat(stats.totalPersons()).isEqualTo(10L);
        assertThat(stats.totalAddresses()).isEqualTo(15L);
        assertThat(stats.totalContacts()).isEqualTo(20L);
        assertThat(stats.totalDependents()).isEqualTo(5L);
        assertThat(stats.recentPersons()).isEqualTo(3L);
        assertThat(stats.averageAge()).isEqualTo(35.5);
    }

    @Test
    @DisplayName("Should find recent persons")
    void shouldFindRecentPersons() {
        // Given
        List<Fisica> mockPersons = Arrays.asList();
        when(findPersonOutputPort.findRecent(10)).thenReturn(mockPersons);

        // When
        List<Fisica> result = dashboardUseCase.findRecent(10);

        // Then
        assertThat(result).isEqualTo(mockPersons);
    }

    @Test
    @DisplayName("Should get persons by month")
    void shouldGetPersonsByMonth() {
        // Given
        List<MonthlyStats> mockStats = Arrays.asList();
        when(findPersonOutputPort.getPersonsByMonth()).thenReturn(mockStats);

        // When
        List<MonthlyStats> result = dashboardUseCase.getPersonsByMonth();

        // Then
        assertThat(result).isEqualTo(mockStats);
    }
}