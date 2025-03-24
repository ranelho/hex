package com.rlti.hex.application.core.domain;

import com.rlti.hex.adapters.input.api.request.PersonRequest;

import java.time.LocalDate;

public class Fisica extends Person {
    String cpf;
    LocalDate birthDate;

    public Fisica(){}

    public Fisica(PersonRequest request) {
        super();
        this.name = request.name();
        this.cpf = request.cpf();
        this.birthDate = request.birthDate();
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
