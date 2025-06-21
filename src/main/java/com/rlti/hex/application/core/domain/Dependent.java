package com.rlti.hex.application.core.domain;

import com.rlti.hex.application.core.domain.enuns.DependentType;

import java.time.LocalDate;

public class Dependent {
    private Long id;
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private DependentType dependentType;

    private Fisica fisica;

    public Dependent() {
    }

    public Dependent(Long id, String name, String cpf, LocalDate birthDate, DependentType dependentType) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.dependentType = dependentType;
    }

    public Dependent(String name, String cpf, LocalDate birthDate, DependentType dependentType) {
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.dependentType = dependentType;
    }

    public Dependent(Fisica fisica, String name, String cpf, LocalDate birthDate, DependentType dependentType) {
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.dependentType = dependentType;
        this.fisica = fisica;
    }

    public Dependent(Fisica fisica, Dependent dependent) {
        this.name = dependent.getName();
        this.cpf = dependent.getCpf();
        this.birthDate = dependent.getBirthDate();
        this.dependentType = dependent.getDependentType();
        this.fisica = fisica;
    }

    public void update(String name, String cpf, LocalDate birthDate, DependentType dependentType) {
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.dependentType = dependentType;
    }

    public void update(Dependent dependent) {
        this.name = dependent.getName();
        this.cpf = dependent.getCpf();
        this.birthDate = dependent.getBirthDate();
        this.dependentType = dependent.getDependentType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public DependentType getDependentType() {
        return dependentType;
    }

    public void setDependentType(DependentType dependentType) {
        this.dependentType = dependentType;
    }

    public Fisica getFisica() {
        return fisica;
    }

    public void setFisica(Fisica fisica) {
        this.fisica = fisica;
    }


}