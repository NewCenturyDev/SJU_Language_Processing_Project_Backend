package com.sju.sju_language_processing.commons.storage;

public enum FileType {
    MEDIA_AUDIO("MEDIA_AUDIO"),
    MEDIA_PHOTO("MEDIA_PHOTO"),
    MEDIA_VIDEO("MEDIA_VIDEO"),
    MEDIA_DOCUMENT("MEDIA_DOCUMENT");

    private final String value;

    FileType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
