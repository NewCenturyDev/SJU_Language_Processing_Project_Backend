package com.sju.sju_language_processing.commons.http;

import lombok.Data;

@Data
public class DTOMetadata {
    private Boolean status;
    private String message;
    private String exception;

    public DTOMetadata(String message) {
        this.status = true;
        this.message = message;
        this.exception = null;
    }

    public DTOMetadata(String message, String exception) {
        this.status = false;
        this.message = message;
        this.exception = exception;
    }
}
