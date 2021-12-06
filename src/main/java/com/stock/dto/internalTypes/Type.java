package com.stock.dto.internalTypes;

public enum Type {

    INFO("Info","type Info"),
    ERROR("Error","type Error");

    private final String name;
    private final String description;

    Type(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
