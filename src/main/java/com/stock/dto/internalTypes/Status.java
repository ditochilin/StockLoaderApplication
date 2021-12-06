package com.stock.dto.internalTypes;


public enum Status {

    NEW("NEW", "New notification"),
    COMPLETED("Compleated", "Completed notification");

    private final String name;
    private final String description;

    Status(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
