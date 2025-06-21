package com.rlti.hex.application.core.domain;

public class Contact {
    private Long id;
    private String email;
    private String ddd;
    private String telephoneNumber;

    private Fisica fisica;

    public Contact() {
    }

    public Contact(Long id, String email, String ddd, String telephoneNumber) {
        validatePhone(ddd, telephoneNumber);
        this.id = id;
        this.email = email;
        this.ddd = ddd;
        this.telephoneNumber = telephoneNumber;
    }

    public Contact(String email, String ddd, String telephoneNumber) {
        validatePhone(ddd, telephoneNumber);
        this.email = email;
        this.ddd = ddd;
        this.telephoneNumber = telephoneNumber;
    }

    public Contact(Fisica fisica, String email, String ddd, String telephoneNumber) {
        validatePhone(ddd, telephoneNumber);
        this.fisica = fisica;
        this.email = email;
        this.ddd = ddd;
        this.telephoneNumber = telephoneNumber;
    }

    public Contact(Fisica fisica, Contact contact) {
        validatePhone(contact.getDdd(), contact.getTelephoneNumber());
        this.fisica = fisica;
        this.email = contact.getEmail();
        this.ddd = contact.getDdd();
        this.telephoneNumber = contact.getTelephoneNumber();
    }

    public void update(String email, String ddd, String telephoneNumber) {
        validatePhone(ddd, telephoneNumber);
        this.email = email;
        this.ddd = ddd;
        this.telephoneNumber = telephoneNumber;
    }

    public void update(Contact contact) {
        validatePhone(contact.getDdd(), contact.getTelephoneNumber());
        this.email = contact.getEmail();
        this.ddd = contact.getDdd();
        this.telephoneNumber = contact.getTelephoneNumber();
    }

    private void validatePhone(String ddd, String telephoneNumber) {
        if (telephoneNumber == null || telephoneNumber.isBlank()) {
            throw new IllegalArgumentException("Telefone é obrigatório");
        }
        // Telefones corporativos: 0800, 4004, 3003, 3004, 0800X, etc
        if (telephoneNumber.startsWith("0800") || telephoneNumber.startsWith("4004") ||
            telephoneNumber.startsWith("3003") || telephoneNumber.startsWith("3004")) {
            // Não exige DDD
            return;
        }
        // Celular: 9 dígitos, DDD obrigatório
        if (telephoneNumber.length() == 9 && ddd != null && !ddd.isBlank()) {
            return;
        }
        // Fixo: 8 dígitos, DDD obrigatório
        if (telephoneNumber.length() == 8 && ddd != null && !ddd.isBlank()) {
            return;
        }
        throw new IllegalArgumentException("Telefone ou DDD inválido para o tipo informado");
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