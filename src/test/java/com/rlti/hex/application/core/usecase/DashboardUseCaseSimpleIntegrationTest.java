package com.rlti.hex.application.core.usecase;

import com.rlti.hex.adapters.input.api.response.DashboardStats;
import com.rlti.hex.adapters.input.api.response.MonthlyStats;
import com.rlti.hex.application.core.domain.Address;
import com.rlti.hex.application.core.domain.Contact;
import com.rlti.hex.application.core.domain.Dependent;
import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.core.domain.enuns.DependentType;
import com.rlti.hex.application.core.domain.enuns.MaritalStatus;
import com.rlti.hex.application.port.output.DashboardOutputPort;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("DashboardUseCase Simple Integration Tests")
class DashboardUseCaseSimpleIntegrationTest {

    @Mock
    private DashboardOutputPort dashboardOutputPort;

    @Mock
    private FindPersonOutputPort findPersonOutputPort;

    private DashboardUseCase dashboardUseCase;

    @BeforeEach
    void setUp() {
        dashboardUseCase = new DashboardUseCase(findPersonOutputPort, dashboardOutputPort);
    }

    @Test
    @DisplayName("Should return dashboard statistics with mocked data")
    void shouldReturnDashboardStatisticsWithMockedData() {
        // Given
        when(dashboardOutputPort.countPersons()).thenReturn(10L);
        when(dashboardOutputPort.countAddresses()).thenReturn(8L);
        when(dashboardOutputPort.countContacts()).thenReturn(15L);
        when(dashboardOutputPort.countDependents()).thenReturn(5L);
        when(dashboardOutputPort.countRecentPersons()).thenReturn(2L);
        when(dashboardOutputPort.calculateAverageAge()).thenReturn(35.5);
        when(dashboardOutputPort.getPersonsByMonth()).thenReturn(List.of(
            new MonthlyStats("Janeiro", 3L),
            new MonthlyStats("Fevereiro", 2L)
        ));
        when(dashboardOutputPort.getTopCities()).thenReturn(List.of());

        // When
        DashboardStats stats = dashboardUseCase.getStats();

        // Then
        assertThat(stats).isNotNull();
        assertThat(stats.totalPersons()).isEqualTo(10L);
        assertThat(stats.totalAddresses()).isEqualTo(8L);
        assertThat(stats.totalContacts()).isEqualTo(15L);
        assertThat(stats.totalDependents()).isEqualTo(5L);
        assertThat(stats.averageAge()).isEqualTo(35.5);
        assertThat(stats.recentPersons()).isEqualTo(2L);
        assertThat(stats.personsByMonth()).hasSize(2);
    }

    @Test
    @DisplayName("Should return monthly statistics with mocked data")
    void shouldReturnMonthlyStatisticsWithMockedData() {
        // Given
        List<MonthlyStats> expectedStats = List.of(
            new MonthlyStats("Janeiro", 5L),
            new MonthlyStats("Fevereiro", 3L),
            new MonthlyStats("Março", 7L)
        );
        when(findPersonOutputPort.getPersonsByMonth()).thenReturn(expectedStats);

        // When
        List<MonthlyStats> monthlyStats = dashboardUseCase.getPersonsByMonth();

        // Then
        assertThat(monthlyStats).isNotNull();
        assertThat(monthlyStats).hasSize(3);
        assertThat(monthlyStats.getFirst().month()).isEqualTo("Janeiro");
        assertThat(monthlyStats.getFirst().count()).isEqualTo(5L);
    }

    @Test
    @DisplayName("Should return recent persons with mocked data")
    void shouldReturnRecentPersonsWithMockedData() {
        // Given
        List<Fisica> expectedPersons = List.of(
            createTestPerson("Recent Person 1", "11111111111"),
            createTestPerson("Recent Person 2", "22222222222"),
            createTestPerson("Recent Person 3", "33333333333")
        );
        when(findPersonOutputPort.findRecent(10)).thenReturn(expectedPersons);

        // When
        List<Fisica> recentPersons = dashboardUseCase.findRecent(10);

        // Then
        assertThat(recentPersons).isNotNull();
        assertThat(recentPersons).hasSize(3);
        assertThat(recentPersons.get(0).getName()).isEqualTo("Recent Person 1");
    }

    @Test
    @DisplayName("Should handle empty data gracefully")
    void shouldHandleEmptyDataGracefully() {
        // Given
        when(dashboardOutputPort.countPersons()).thenReturn(0L);
        when(dashboardOutputPort.countAddresses()).thenReturn(0L);
        when(dashboardOutputPort.countContacts()).thenReturn(0L);
        when(dashboardOutputPort.countDependents()).thenReturn(0L);
        when(dashboardOutputPort.countRecentPersons()).thenReturn(0L);
        when(dashboardOutputPort.calculateAverageAge()).thenReturn(0.0);
        when(dashboardOutputPort.getTopCities()).thenReturn(List.of());

        // When
        DashboardStats stats = dashboardUseCase.getStats();

        // Then
        assertThat(stats).isNotNull();
        assertThat(stats.totalPersons()).isZero();
        assertThat(stats.totalAddresses()).isZero();
        assertThat(stats.totalContacts()).isZero();
        assertThat(stats.totalDependents()).isZero();
        assertThat(stats.averageAge()).isEqualTo(0.0);
        assertThat(stats.recentPersons()).isZero();
    }

    private Fisica createTestPerson(String name, String cpf) {
        Address address = Address.builder()
                .street("Rua Teste")
                .neighborhood("Bairro Teste")
                .city("Cidade Teste")
                .state("SP")
                .zipCode("12345678")
                .build();

        Contact contact = new Contact("teste@email.com", "11", "999999999");

        Dependent dependent = new Dependent("Dependente Teste", "12345678901", LocalDate.of(2010, 1, 1), DependentType.FILHO);

        return Fisica.builder()
                .name(name)
                .cpf(cpf)
                .rg("123456789")
                .rgIssuer("SSP")
                .birthDate(LocalDate.of(1990, 1, 1))
                .nameMother("Mãe Teste")
                .nameFather("Pai Teste")
                .maritalStatus(MaritalStatus.SOLTEIRO)
                .profession("Profissão Teste")
                .nationality("Brasileira")
                .addresses(List.of(address))
                .contacts(List.of(contact))
                .dependents(List.of(dependent))
                .build();
    }
}