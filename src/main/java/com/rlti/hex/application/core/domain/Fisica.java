package com.rlti.hex.application.core.domain;

import com.rlti.hex.handler.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Fisica extends Person {
    private String cpf;
    private LocalDate birthDate;
    private String nameMother;
    private String nameFather;
    private List<Contact> contacts;
    private List<Dependent> dependents;

    public Fisica() {
    }

    private Fisica(Builder builder) {
        super();
        this.name = builder.name;
        this.cpf = builder.cpf;
        this.birthDate = builder.birthDate;
        this.nameMother = builder.nameMother;
        this.nameFather = builder.nameFather;
        
        this.addresses = Optional.ofNullable(builder.addresses).orElse(new ArrayList<>());
        this.contacts = Optional.ofNullable(builder.contacts).orElse(new ArrayList<>());
        this.dependents = Optional.ofNullable(builder.dependents).orElse(new ArrayList<>());
        
        setupRelationships();
    }

    public static Builder builder() {
        return new Builder();
    }

    private void setupRelationships() {
        this.addresses.forEach(address -> address.setPerson(this));
        this.contacts.forEach(contact -> contact.setFisica(this));
        this.dependents.forEach(dependent -> dependent.setFisica(this));
    }

    public static final class Builder {
        private String name;
        private String cpf;
        private LocalDate birthDate;
        private String nameMother;
        private String nameFather;
        private List<Address> addresses;
        private List<Contact> contacts;
        private List<Dependent> dependents;

        private Builder() {}

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder cpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public Builder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder nameMother(String nameMother) {
            this.nameMother = nameMother;
            return this;
        }

        public Builder nameFather(String nameFather) {
            this.nameFather = nameFather;
            return this;
        }

        public Builder addresses(List<Address> addresses) {
            this.addresses = addresses;
            return this;
        }

        public Builder contacts(List<Contact> contacts) {
            this.contacts = contacts;
            return this;
        }

        public Builder dependents(List<Dependent> dependents) {
            this.dependents = dependents;
            return this;
        }

        public Fisica build() {
            return new Fisica(this);
        }
    }

    public void update(Fisica request) {
        this.name = request.getName();
        updateOrAddAddress(request.getAddresses());
        updateOrAddContact(request.getContacts());
        updateOrAddDependent(request.getDependents());
    }

    public void updateOrAddAddress(List<Address> addresses) {
        if (this.addresses == null) this.addresses = new ArrayList<>();
        
        Optional.ofNullable(addresses).ifPresent(addressList -> 
            addressList.forEach(address -> {
                if (address.getId() != null) {
                    findExistingAddress(address.getId()).update(address);
                } else {
                    address.setPerson(this);
                    this.addresses.add(address);
                }
            })
        );
    }
    
    private Address findExistingAddress(Long id) {
        return this.addresses.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
    }

    public void updateOrAddContact(List<Contact> contacts) {
        if (this.contacts == null) this.contacts = new ArrayList<>();
        
        Optional.ofNullable(contacts).ifPresent(contactList -> 
            contactList.forEach(contact -> {
                if (contact.getId() != null) {
                    findExistingContact(contact.getId()).update(contact);
                } else {
                    contact.setFisica(this);
                    this.contacts.add(contact);
                }
            })
        );
    }
    
    private Contact findExistingContact(Long id) {
        return this.contacts.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
    }

    public void updateOrAddDependent(List<Dependent> dependents) {
        if (this.dependents == null) this.dependents = new ArrayList<>();
        
        Optional.ofNullable(dependents).ifPresent(dependentList -> 
            dependentList.forEach(dependent -> {
                if (dependent.getId() != null) {
                    findExistingDependent(dependent.getId()).update(dependent);
                } else {
                    dependent.setFisica(this);
                    this.dependents.add(dependent);
                }
            })
        );
    }
    
    private Dependent findExistingDependent(Long id) {
        return this.dependents.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Dependent not found"));
    }

    public String getCpf() { return cpf; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getNameMother() { return nameMother; }
    public String getNameFather() { return nameFather; }
    public List<Contact> getContacts() { return contacts != null ? List.copyOf(contacts) : List.of(); }
    public List<Dependent> getDependents() { return dependents != null ? List.copyOf(dependents) : List.of(); }
    
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public void setNameMother(String nameMother) { this.nameMother = nameMother; }
    public void setNameFather(String nameFather) { this.nameFather = nameFather; }
    
    public void setContacts(List<Contact> contacts) {
        this.contacts = Optional.ofNullable(contacts).orElse(new ArrayList<>());
    }
    
    public void setDependents(List<Dependent> dependents) {
        this.dependents = Optional.ofNullable(dependents).orElse(new ArrayList<>());
    }
}