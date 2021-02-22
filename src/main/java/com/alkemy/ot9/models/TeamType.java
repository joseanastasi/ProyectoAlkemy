package com.alkemy.ot9.models;

public enum TeamType {
    RESPONSABLE("Responsable"), INCHARGE("Encargada"), COORDINATOR("Coordinador");

    private final String name;

    TeamType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
