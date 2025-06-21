package com.rlti.hex.application.core.domain.enuns;

public enum DependentType {
    PAI("Pai"),
    MAE("Mãe"),
    FILHO("Filho"),
    FILHA("Filha"),
    SOBRINHO("Sobrinho"),
    SOBRINHA("Sobrinha"),
    ENTIADO("Entiado"),
    ENTIADA("Entiada");

    private final String description;

    DependentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static DependentType fromDescription(String description) {
        for (DependentType dependentType : DependentType.values()) {
            if (dependentType.getDescription().equals(description)) {
                return dependentType;
            }
        }
        return null;
    }
}
