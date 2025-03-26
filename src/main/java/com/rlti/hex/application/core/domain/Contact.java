package com.rlti.hex.application.core.domain;

import com.rlti.hex.adapters.input.api.request.ContactRequest;
import jakarta.validation.Valid;

public class Contact {
    private Long id;
    private String email;
    private String ddd;
    private String telephoneNumber;

    private Fisica fisica;

    public Contact() {
    }

    public Contact(Fisica fisica, ContactRequest request) {
        this.fisica = fisica;
        this.email = request.email();
        this.ddd = request.ddd();
        this.telephoneNumber = request.telephoneNumber();
    }

    public Contact(@Valid ContactRequest request) {
        this.email = request.email();
        this.ddd = request.ddd();
        this.telephoneNumber = request.telephoneNumber();
    }

    public void update(ContactRequest request) {
        this.email = request.email();
        this.ddd = request.ddd();
        this.telephoneNumber = request.telephoneNumber();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public Fisica getFisica() {
        return fisica;
    }

    public void setFisica(Fisica fisica) {
        this.fisica = fisica;
    }


}
