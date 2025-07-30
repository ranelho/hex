package com.rlti.hex.application.core.domain;

public class Address {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String neighborhood;
    private String zipCode;
    private String country;
    private String number;

    private Person person;

    // Construtor padrão para frameworks
    public Address() {
    }

    // Construtor privado usado pelo Builder
    private Address(Builder builder) {
        this.id = builder.id;
        this.street = builder.street;
        this.city = builder.city;
        this.state = builder.state;
        this.neighborhood = builder.neighborhood;
        this.zipCode = builder.zipCode;
        this.country = builder.country;
        this.number = builder.number;
        this.person = builder.person;
    }

    /**
     * Construtor que utiliza AddressData para encapsular todos os campos de endereço
     *
     * @param id   o ID do endereço
     * @param data objeto que encapsula todos os dados do endereço
     */
    public Address(Long id, AddressData data) {
        this(builder()
                .id(id)
                .street(data.street())
                .city(data.city())
                .state(data.state())
                .neighborhood(data.neighborhood())
                .zipCode(data.zipCode())
                .country(data.country())
                .number(data.number()));
    }

    // Método estático para criar um Builder
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Cria um novo endereço a partir de dados encapsulados.
     *
     * @param data os dados do endereço
     * @return um novo objeto Address
     */
    public static Address createAddress(AddressData data) {
        return builder()
                .street(data.street())
                .city(data.city())
                .state(data.state())
                .zipCode(data.zipCode())
                .country(data.country())
                .number(data.number())
                .neighborhood(data.neighborhood())
                .build();
    }

    /**
     * Cria um novo endereço associado a uma pessoa.
     *
     * @param person a pessoa associada ao endereço
     * @param data   os dados do endereço
     * @return um novo objeto Address
     */
    public static Address createAddressForPerson(Person person, AddressData data) {
        return builder()
                .person(person)
                .street(data.street())
                .city(data.city())
                .state(data.state())
                .zipCode(data.zipCode())
                .country(data.country())
                .number(data.number())
                .neighborhood(data.neighborhood())
                .build();
    }

    /**
     * Atualiza os campos deste endereço com os valores de outro endereço.
     * Mantém o id e a pessoa associada intactos.
     *
     * @param address o endereço fonte dos dados
     */
    public void update(Address address) {
        // Extrair os dados do endereço para um AddressData
        AddressData data = AddressData.from(address);

        // Atualizar os campos usando os dados extraídos
        this.street = data.street();
        this.city = data.city();
        this.state = data.state();
        this.neighborhood = data.neighborhood();
        this.zipCode = data.zipCode();
        this.country = data.country();
        this.number = data.number();
    }

    // O método toBuilder() foi removido por não estar sendo utilizado

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", zipCode='" + zipCode + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    /**
     * Classe Builder para construção fluente de objetos Address.
     */
    public static class Builder {
        private Long id;
        private String street;
        private String city;
        private String state;
        private String neighborhood;
        private String zipCode;
        private String country;
        private String number;
        private Person person;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder neighborhood(String neighborhood) {
            this.neighborhood = neighborhood;
            return this;
        }

        public Builder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public Builder person(Person person) {
            this.person = person;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}