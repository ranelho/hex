package com.rlti.hex.application.core.domain.enuns;

public enum MaritalStatus {
    SOLTEIRO("Solteiro(a)"),
    CASADO("Casado(a)"),
    DIVORCIADO("Divorciado(a)"),
    VIUVO("Viúvo(a)"),
    SEPARADO("Separado(a)"),
    UNIAO_ESTAVEL("União Estável");

    private final String description;

    MaritalStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static MaritalStatus fromDescription(String description) {
        for (MaritalStatus maritalStatus : MaritalStatus.values()) {
            if (maritalStatus.getDescription().equals(description)) {
                return maritalStatus;
            }
        }
        return null;
    }
}