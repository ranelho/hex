package com.rlti.hex.adapters.output.entity;

import com.rlti.hex.application.core.domain.enuns.Gender;
import com.rlti.hex.application.core.domain.enuns.MaritalStatus;
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

    @Column(name = "rg")
    private String rg;

    @Column(name = "rg_issuer")
    private String rgIssuer;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "name_mother")
    private String nameMother;

    @Column(name = "name_father")
    private String nameFather;

    @Column(name = "marital_status")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(name = "profession")
    private String profession;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    @Column(name = "emergency_phone")
    private String emergencyPhone;

    @OneToMany(mappedBy = "fisica", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ContactEntity> contacts;

    @OneToMany(mappedBy = "fisica", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<DependentEntity> dependents;
}

