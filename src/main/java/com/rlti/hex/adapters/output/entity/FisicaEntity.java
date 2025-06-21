package com.rlti.hex.adapters.output.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "person_fisica")
@DiscriminatorValue("FISICA")
@EqualsAndHashCode(callSuper = true)
public class FisicaEntity extends PersonEntity {

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;
    private LocalDate birthDate;
    private String nameMother;
    private String nameFather;

    @OneToMany(mappedBy = "fisica", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContactEntity> contacts;

    @OneToMany(mappedBy = "fisica", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DependentEntity> dependents;
}

