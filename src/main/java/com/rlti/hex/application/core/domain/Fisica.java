package com.rlti.hex.application.core.domain;

import com.rlti.hex.handler.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Fisica extends Person {
    private final String cpf;
    private final LocalDate birthDate;
    private final String nameMother;
    private final String nameFather;
    private final List<Contact> contacts;
    private final List<Dependent> dependents;

    private Fisica(Builder builder) {
        super();
        this.name = Objects.requireNonNull(builder.name, "Name is required");
        this.cpf = Objects.requireNonNull(builder.cpf, "CPF is required");
        this.birthDate = Objects.requireNonNull(builder.birthDate, "Birth date is required");
        this.nameMother = builder.nameMother;
        this.nameFather = builder.nameFather;
        
        this.addresses = new ArrayList<>(builder.addresses != null ? builder.addresses : Collections.emptyList());
        this.contacts = new ArrayList<>(builder.contacts != null ? builder.contacts : Collections.emptyList());
        this.dependents = new ArrayList<>(builder.dependents != null ? builder.dependents : Collections.emptyList());
        
        setupRelationships();
    }

    public static Builder builder() {
        return new Builder();
    }

    private void setupRelationships() {
        addresses.forEach(address -> address.setPerson(this));
        contacts.forEach(contact -> contact.setFisica(this));
        dependents.forEach(dependent -> dependent.setFisica(this));
    }

    public void update(Fisica request) {
        Objects.requireNonNull(request, "Update request cannot be null");
        this.name = request.getName();
        updateOrAddAddress(request.getAddresses());
        updateOrAddContact(request.getContacts());
        updateOrAddDependent(request.getDependents());
    }

    public void updateOrAddAddress(List<Address> newAddresses) {
        if (newAddresses == null) return;
        
        newAddresses.forEach(address -> {
            if (address.getId() != null) {
                findExistingAddress(address.getId()).update(address);
            } else {
                address.setPerson(this);
                addresses.add(address);
            }
        });
    }
    
    private Address findExistingAddress(Long id) {
        return addresses.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
    }

    public void updateOrAddContact(List<Contact> newContacts) {
        if (newContacts == null) return;
        
        newContacts.forEach(contact -> {
            if (contact.getId() != null) {
                findExistingContact(contact.getId()).update(contact);
            } else {
                contact.setFisica(this);
                contacts.add(contact);
            }
        });
    }
    
    private Contact findExistingContact(Long id) {
        return contacts.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));
    }

    public void updateOrAddDependent(List<Dependent> newDependents) {
        if (newDependents == null) return;
        
        newDependents.forEach(dependent -> {
            if (dependent.getId() != null) {
                findExistingDependent(dependent.getId()).update(dependent);
            } else {
                dependent.setFisica(this);
                dependents.add(dependent);
            }
        });
    }
    
    private Dependent findExistingDependent(Long id) {
        return dependents.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Dependent not found with id: " + id));
    }

    // Getters
    public String getCpf() { return cpf; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getNameMother() { return nameMother; }
    public String getNameFather() { return nameFather; }
    public List<Contact> getContacts() { return Collections.unmodifiableList(contacts); }
    public List<Dependent> getDependents() { return Collections.unmodifiableList(dependents); }

    @Override
    public String toString() {
        return "Fisica{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", birthDate=" + birthDate +
                ", nameMother='" + nameMother + '\'' +
                ", nameFather='" + nameFather + '\'' +
                ", addresses=" + (addresses != null ? addresses.size() : 0) +
                ", contacts=" + (contacts != null ? contacts.size() : 0) +
                ", dependents=" + (dependents != null ? dependents.size() : 0) +
                '}';
    }

            /**
             * Classe Builder implementada como um record para imutabilidade e melhor performance
             */
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

        /**
         * Factory method para criar instâncias de Builder com valores iniciais
         * utilizando records para imutabilidade
         */
        public static Builder fromPrototype(BuilderPrototype prototype) {
            var builder = new Builder();
            builder.name = prototype.name();
            builder.cpf = prototype.cpf();
            builder.birthDate = prototype.birthDate();
            builder.nameMother = prototype.nameMother();
            builder.nameFather = prototype.nameFather();
            builder.addresses = prototype.addresses();
            builder.contacts = prototype.contacts();
            builder.dependents = prototype.dependents();
            return builder;
        }

        /**
         * Record para representar o protótipo do builder
         */
        public record BuilderPrototype(
            String name,
            String cpf,
            LocalDate birthDate,
            String nameMother,
            String nameFather,
            List<Address> addresses,
            List<Contact> contacts,
            List<Dependent> dependents
        ) {}

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
}