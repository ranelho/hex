package com.rlti.hex.application.core.domain;

/**
 * Record que encapsula os dados de um endereço para reduzir o número de parâmetros nos construtores.
 */
public record AddressData(
    String street,
    String city,
    String state,
    String neighborhood,
    String zipCode,
    String country,
    String number
) {
    // Construtor vazio para facilitar a criação
    public static AddressData empty() {
        return new AddressData(null, null, null, null, null, null, null);
    }

    // Cria uma cópia com os dados de um objeto Address existente
    public static AddressData from(Address address) {
        return new AddressData(
            address.getStreet(),
            address.getCity(),
            address.getState(),
            address.getNeighborhood(),
            address.getZipCode(),
            address.getCountry(),
            address.getNumber()
        );
    }
}
