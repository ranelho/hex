package com.rlti.hex.application.core.domain;

import com.rlti.hex.adapters.input.api.request.DependentRequest;
import com.rlti.hex.application.core.domain.enuns.DependentType;
import jakarta.validation.Valid;

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

    public Dependent(Fisica fisica, DependentRequest request) {
        this.name = request.name();
        this.cpf = request.cpf();
        this.birthDate = request.birthDate();
        this.dependentType = DependentType.fromDescription(request.dependentType());
        this.fisica = fisica;
    }

    public Dependent(@Valid DependentRequest request) {
        this.name = request.name();
        this.cpf = request.cpf();
        this.birthDate = request.birthDate();
        this.dependentType = DependentType.fromDescription(request.dependentType());
    }

    public void update(DependentRequest request) {
        this.name = request.name();
        this.cpf = request.cpf();
        this.birthDate = request.birthDate();
        this.dependentType = DependentType.fromDescription(request.dependentType());
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
