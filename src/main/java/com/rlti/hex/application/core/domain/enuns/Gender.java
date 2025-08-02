package com.rlti.hex.application.core.domain.enuns;

public enum Gender {
    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    OUTRO("Outro"),
    PREFIRO_NAO_INFORMAR("Prefiro não informar");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Gender fromDescription(String description) {
        for (Gender gender : Gender.values()) {
            if (gender.getDescription().equals(description)) {
                return gender;
            }
        }
        return null;
    }
}