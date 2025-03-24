package com.rlti.hex.adapters.output.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "person_fisica")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class FisicaEntity extends PersonEntity {
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;
    private LocalDate birthDate;
    private String nameMother;
    private String nameFather;
}
