package com.rlti.hex.adapters.output.entity;

import com.rlti.hex.application.core.domain.enuns.DependentType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "dependent")
public class DependentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cpf;
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private DependentType dependentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fisica_id")
    private FisicaEntity fisica;
}
