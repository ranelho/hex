package com.rlti.hex.application.core.domain;

import com.rlti.hex.application.core.domain.enuns.Gender;
import com.rlti.hex.application.core.domain.enuns.MaritalStatus;
import com.rlti.hex.handler.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public final class Fisica extends Person {
    private String cpf;
    private String rg;
    private String rgIssuer;
    private LocalDate birthDate;
    private String nameMother;
    private String nameFather;
    private MaritalStatus maritalStatus;
    private String profession;
    private String nationality;
    private Gender gender;
    private String emergencyContact;
    private String emergencyPhone;
    private List<Contact> contacts;
    private List<Dependent> dependents;

    private Fisica(Builder builder) {
        super();
        this.name = builder.name;
        this.cpf = builder.cpf;
        this.rg = builder.rg;
        this.rgIssuer = builder.rgIssuer;
        this.birthDate = builder.birthDate;
        this.nameMother = builder.nameMother;
        this.nameFather = builder.nameFather;
        this.maritalStatus = builder.maritalStatus;
        this.profession = builder.profession;
        this.nationality = builder.nationality;
        this.gender = builder.gender;
        this.emergencyContact = builder.emergencyContact;
        this.emergencyPhone = builder.emergencyPhone;

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
        this.rg = request.getRg();
        this.rgIssuer = request.getRgIssuer();
        this.nameMother = request.getNameMother();
        this.nameFather = request.getNameFather();
        this.birthDate = request.getBirthDate();
        this.maritalStatus = request.getMaritalStatus();
        this.profession = request.getProfession();
        this.nationality = request.getNationality();
        this.gender = request.getGender();
        this.emergencyContact = request.getEmergencyContact();
        this.emergencyPhone = request.getEmergencyPhone();
        updateOrAddAddress(request.getAddresses());
        updateOrAddContact(request.getContacts());
        updateOrAddDependent(request.getDependents());
    }

    public void updateOrAddAddress(List<Address> newAddresses) {
        if (newAddresses == null) return;

        Set<Long> newIds = newAddresses.stream()
                .map(Address::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        addresses.removeIf(existing ->
                existing.getId() != null && !newIds.contains(existing.getId())
        );

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

        Set<Long> newIds = newContacts.stream()
                .map(Contact::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        contacts.removeIf(existing ->
                existing.getId() != null && !newIds.contains(existing.getId())
        );

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

        Set<Long> newIds = newDependents.stream()
                .map(Dependent::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        dependents.removeIf(existing ->
                existing.getId() != null && !newIds.contains(existing.getId())
        );

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

    public String getCpf() {
        return cpf;
    }

    public String getRg() {
        return rg;
    }

    public String getRgIssuer() {
        return rgIssuer;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getNameMother() {
        return nameMother;
    }

    public String getNameFather() {
        return nameFather;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public String getProfession() {
        return profession;
    }

    public String getNationality() {
        return nationality;
    }

    public Gender getGender() {
        return gender;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public List<Contact> getContacts() {
        return Collections.unmodifiableList(contacts);
    }

    public List<Dependent> getDependents() {
        return Collections.unmodifiableList(dependents);
    }

    @Override
    public String toString() {
        return "Fisica{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", rg='" + rg + '\'' +
                ", rgIssuer='" + rgIssuer + '\'' +
                ", birthDate=" + birthDate +
                ", nameMother='" + nameMother + '\'' +
                ", nameFather='" + nameFather + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", profession='" + profession + '\'' +
                ", nationality='" + nationality + '\'' +
                ", gender='" + gender + '\'' +
                ", emergencyContact='" + emergencyContact + '\'' +
                ", emergencyPhone='" + emergencyPhone + '\'' +
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
        private String rg;
        private String rgIssuer;
        private LocalDate birthDate;
        private String nameMother;
        private String nameFather;
        private MaritalStatus maritalStatus;
        private String profession;
        private String nationality;
        private Gender gender;
        private String emergencyContact;
        private String emergencyPhone;
        private List<Address> addresses;
        private List<Contact> contacts;
        private List<Dependent> dependents;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder cpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public Builder rg(String rg) {
            this.rg = rg;
            return this;
        }

        public Builder rgIssuer(String rgIssuer) {
            this.rgIssuer = rgIssuer;
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

        public Builder maritalStatus(MaritalStatus maritalStatus) {
            this.maritalStatus = maritalStatus;
            return this;
        }

        public Builder profession(String profession) {
            this.profession = profession;
            return this;
        }

        public Builder nationality(String nationality) {
            this.nationality = nationality;
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder emergencyContact(String emergencyContact) {
            this.emergencyContact = emergencyContact;
            return this;
        }

        public Builder emergencyPhone(String emergencyPhone) {
            this.emergencyPhone = emergencyPhone;
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

        /**
         * Record para representar o protótipo do builder
         */
        public record BuilderPrototype(
                String name,
                String cpf,
                String rg,
                String rgIssuer,
                LocalDate birthDate,
                String nameMother,
                String nameFather,
                MaritalStatus maritalStatus,
                String profession,
                String nationality,
                Gender gender,
                String emergencyContact,
                String emergencyPhone,
                List<Address> addresses,
                List<Contact> contacts,
                List<Dependent> dependents
        ) {
        }
    }
}