package com.rlti.hex.application.core.domain;

import com.rlti.hex.adapters.input.api.request.PersonRequest;

import java.time.LocalDate;

public class Fisica extends Person {
    private String cpf;
    private LocalDate birthDate;
    private String nameMother;
    private String nameFather;

    public Fisica() {
    }

    public Fisica(PersonRequest request) {
        super();
        this.name = request.name();
        this.cpf = request.cpf();
        this.birthDate = request.birthDate();

        this.addresses = request.addresses().stream()
                .map(addressRequest -> {
                    Address address = new Address(addressRequest);
                    address.setPerson(this);
                    return address;
                })
                .toList();
    }

    public String getCpf() {
        return cpf;
    }

    public String getNameMother() {
        return nameMother;
    }

    public void setNameMother(String nameMother) {
        this.nameMother = nameMother;
    }

    public String getNameFather() {
        return nameFather;
    }

    public void setNameFather(String nameFather) {
        this.nameFather = nameFather;
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
}
