package com.example.documents.model.enums;

public enum Gender {
    MALE("М"),
    FEMALE("Ж");
    private final String nameValue;

    Gender(String nameValue) {
        this.nameValue = nameValue;
    }

    public String getNameValue() {
        return nameValue;
    }
}
