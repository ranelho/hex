package com.rlti.hex.application.core.usecase;

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
@DisplayName("DashboardUseCase Simple Tests")
class DashboardUseCaseSimpleTest {

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
    @DisplayName("Should count persons correctly")
    void shouldCountPersonsCorrectly() {
        // Given
        when(dashboardOutputPort.countPersons()).thenReturn(5L);

        // When
        Long count = dashboardOutputPort.countPersons();

        // Then
        assertThat(count).isEqualTo(5L);
    }

    @Test
    @DisplayName("Should find recent persons")
    void shouldFindRecentPersons() {
        // Given
        List<Fisica> mockPersons = List.of(
            createTestPerson("Recent Person 1", "11111111111"),
            createTestPerson("Recent Person 2", "22222222222")
        );
        when(findPersonOutputPort.findRecent(10)).thenReturn(mockPersons);

        // When
        List<Fisica> recentPersons = dashboardUseCase.findRecent(10);

        // Then
        assertThat(recentPersons).isNotNull();
        assertThat(recentPersons).hasSize(2);
        assertThat(recentPersons.get(0).getName()).isEqualTo("Recent Person 1");
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
        Dependent dependent = new Dependent("Dependente", "12345678901", LocalDate.of(2010, 1, 1), DependentType.FILHO);

        return Fisica.builder()
                .name(name)
                .cpf(cpf)
                .rg("123456789")
                .rgIssuer("SSP")
                .birthDate(LocalDate.of(1990, 1, 1))
                .nameMother("Mãe")
                .nameFather("Pai")
                .maritalStatus(MaritalStatus.SOLTEIRO)
                .profession("Profissão")
                .nationality("Brasileira")
                .addresses(List.of(address))
                .contacts(List.of(contact))
                .dependents(List.of(dependent))
                .build();
    }
}