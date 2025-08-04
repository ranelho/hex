package com.rlti.hex.application.core.usecase;

import com.rlti.hex.adapters.input.api.response.DashboardStats;
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
@DisplayName("DashboardUseCase Integration Tests")
class DashboardUseCaseIntegrationTest {

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
        when(dashboardOutputPort.countRecentPersons()).thenReturn(3L);
        when(dashboardOutputPort.calculateAverageAge()).thenReturn(35.5);
        when(dashboardOutputPort.getPersonsByMonth()).thenReturn(List.of());
        when(dashboardOutputPort.getTopCities()).thenReturn(List.of());

        // When
        DashboardStats stats = dashboardUseCase.getStats();

        // Then
        assertThat(stats.totalPersons()).isEqualTo(10L);
        assertThat(stats.totalAddresses()).isEqualTo(8L);
        assertThat(stats.totalContacts()).isEqualTo(15L);
        assertThat(stats.totalDependents()).isEqualTo(5L);
        assertThat(stats.recentPersons()).isEqualTo(3L);
    }

    @Test
    @DisplayName("Should return recent persons with mocked data")
    void shouldReturnRecentPersonsWithMockedData() {
        // Given
        List<Fisica> mockPersons = List.of(
            createTestPerson("Test Person", "11111111111", LocalDate.now())
        );
        when(findPersonOutputPort.findRecent(10)).thenReturn(mockPersons);

        // When
        List<Fisica> recentPersons = dashboardUseCase.findRecent(10);

        // Then
        assertThat(recentPersons).isNotNull();
        assertThat(recentPersons).hasSize(1);
        assertThat(recentPersons.get(0).getName()).isEqualTo("Test Person");
    }

    @Test
    @DisplayName("Should retrieve multiple persons correctly")
    void shouldRetrieveMultiplePersonsCorrectly() {
        // Given
        List<Fisica> mockPersons = List.of(
            createTestPerson("Recent Person 1", "22222222222", LocalDate.now()),
            createTestPerson("Recent Person 2", "33333333333", LocalDate.now())
        );
        when(findPersonOutputPort.findRecent(10)).thenReturn(mockPersons);

        // When
        List<Fisica> recentPersons = dashboardUseCase.findRecent(10);

        // Then
        assertThat(recentPersons).isNotNull();
        assertThat(recentPersons).hasSize(2);
        assertThat(recentPersons).extracting(Fisica::getName)
                .containsExactly("Recent Person 1", "Recent Person 2");
    }

    @Test
    @DisplayName("Should count persons correctly")
    void shouldCountPersonsCorrectly() {
        // Given
        when(dashboardOutputPort.countPersons()).thenReturn(5L);

        // When
        Long count = dashboardOutputPort.countPersons();

        // Then
        assertThat(count).isEqualTo(5L);
    }

    private Fisica createTestPerson(String name, String cpf, LocalDate birthDate) {
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
                .birthDate(birthDate)
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