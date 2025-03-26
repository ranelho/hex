package com.rlti.hex.application.core.domain;

import com.rlti.hex.adapters.input.api.request.AddressRequest;
import com.rlti.hex.adapters.input.api.request.ContactRequest;
import com.rlti.hex.adapters.input.api.request.PersonRequest;
import com.rlti.hex.adapters.input.api.request.PersonUpdateRequest;
import com.rlti.hex.handler.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;

public class Fisica extends Person {
    private String cpf;
    private LocalDate birthDate;
    private String nameMother;
    private String nameFather;

    private List<Contact> contacts;

    public Fisica() {
    }

    public Fisica(PersonRequest request) {
        super();
        this.name = request.name();
        this.cpf = request.cpf();
        this.birthDate = request.birthDate();
        this.nameMother = request.nameMother();
        this.nameFather = request.nameFather();
        if (request.addresses() != null)
            this.addresses = request.addresses().stream()
                    .map(addressRequest -> {
                        Address address = new Address(addressRequest);
                        address.setPerson(this);
                        return address;
                    })
                    .toList();
        if (request.contacts() != null)
            this.contacts = request.contacts().stream()
                    .map(contactRequest -> {
                        Contact contact = new Contact(contactRequest);
                        contact.setFisica(this);
                        return contact;
                    })
                    .toList();
    }

    public void update(PersonUpdateRequest request) {
        this.birthDate = request.birthDate();
        this.name = request.name();
        this.nameMother = request.nameMother();
        this.nameFather = request.nameFather();
    }

    public void updateOrAddAddress(AddressRequest request) {
        if (request.id() != null) {
            var existingAddress = this.addresses.stream()
                    .filter(address -> address.getId().equals(request.id()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
            existingAddress.update(request);
        } else {
            this.addresses.add(new Address(this, request));
        }
    }

    public void updateOrAddContact(ContactRequest request) {
        if (request.id() != null) {
            var existingContact = this.contacts.stream()
                    .filter(contact -> contact.getId().equals(request.id()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
            existingContact.update(request);
        } else {
            this.contacts.add(new Contact(this, request));
        }
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

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}