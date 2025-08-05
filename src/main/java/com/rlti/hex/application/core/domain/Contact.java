package com.rlti.hex.application.core.domain;

import com.rlti.hex.application.core.domain.constants.ApplicationConstants;
import com.rlti.hex.application.core.domain.messages.ErrorMessages;

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
            throw new IllegalArgumentException(ErrorMessages.Phone.REQUIRED);
        }

        // Remove hyphen from the telephone number before validation
        telephoneNumber = telephoneNumber.replace(ApplicationConstants.Phone.PHONE_SEPARATOR, "");

        if (telephoneNumber.startsWith(ApplicationConstants.Phone.TOLL_FREE_PREFIX_0800) || 
                telephoneNumber.startsWith(ApplicationConstants.Phone.TOLL_FREE_PREFIX_4004) ||
                telephoneNumber.startsWith(ApplicationConstants.Phone.TOLL_FREE_PREFIX_3003) || 
                telephoneNumber.startsWith(ApplicationConstants.Phone.TOLL_FREE_PREFIX_3004)) {
            return;
        }
        if (telephoneNumber.length() == ApplicationConstants.Phone.MOBILE_PHONE_LENGTH && ddd != null && !ddd.isBlank()) {
            return;
        }
        if (telephoneNumber.length() == ApplicationConstants.Phone.LANDLINE_PHONE_LENGTH && ddd != null && !ddd.isBlank()) {
            return;
        }
        throw new IllegalArgumentException(ErrorMessages.Phone.INVALID_DDD_OR_NUMBER);
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

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", ddd='" + ddd + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                '}';
    }


}