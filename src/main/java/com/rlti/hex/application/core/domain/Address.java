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

    public Address() {
    }

    // Construtor usado pelo AddressRequest.toDomain()
    public Address(Long id, String street, String city, String state, String neighborhood, String zipCode, String country, String number) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.neighborhood = neighborhood;
        this.zipCode = zipCode;
        this.country = country;
        this.number = number;
    }

    public Address(String street, String city, String state, String zipCode, String country, String number, String neighborhood) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.number = number;
        this.neighborhood = neighborhood;
    }

    public Address(Person person, String street, String city, String state, String zipCode, String country, String number, String neighborhood) {
        this.person = person;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.number = number;
        this.neighborhood = neighborhood;
    }

    public Address(Fisica fisica, Address address) {
        this.person = fisica;
        this.street = address.getStreet();
        this.city = address.getCity();
        this.state = address.getState();
        this.neighborhood = address.getNeighborhood();
        this.zipCode = address.getZipCode();
        this.country = address.getCountry();
        this.number = address.getNumber();
    }

    public void update(Address address) {
        this.street = address.getStreet();
        this.city = address.getCity();
        this.state = address.getState();
        this.neighborhood = address.getNeighborhood();
        this.zipCode = address.getZipCode();
        this.country = address.getCountry();
        this.number = address.getNumber();
    }

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



}