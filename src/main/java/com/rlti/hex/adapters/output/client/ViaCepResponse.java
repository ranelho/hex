package com.rlti.hex.adapters.output.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe que representa a resposta da API ViaCEP.
 * 
 * Exemplo de resposta:
 * {
 *   "cep": "01001-000",
 *   "logradouro": "Praça da Sé",
 *   "complemento": "lado ímpar",
 *   "bairro": "Sé",
 *   "localidade": "São Paulo",
 *   "uf": "SP",
 *   "ibge": "3550308",
 *   "gia": "1004",
 *   "ddd": "11",
 *   "siafi": "7107"
 * }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ViaCepResponse {

    @JsonProperty("cep")
    private String zipCode;

    @JsonProperty("logradouro")
    private String street;

    @JsonProperty("bairro")
    private String neighborhood;

    @JsonProperty("localidade")
    private String city;

    @JsonProperty("uf")
    private String state;

    @JsonProperty("erro")
    private Boolean error;

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
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

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    /**
     * Verifica se a resposta contém um erro ou se os campos importantes estão nulos
     */
    public boolean isValid() {
        return error == null || !error;
    }
}
