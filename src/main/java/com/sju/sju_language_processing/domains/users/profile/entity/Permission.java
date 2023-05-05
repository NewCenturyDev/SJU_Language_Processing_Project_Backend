package com.sju.sju_language_processing.domains.users.profile.entity;

public enum Permission {
    ROOT_ADMIN("ROOT_ADMIN"),
    ADMIN("ADMIN"),
    USER("USER");

    private final String value;
    Permission(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
