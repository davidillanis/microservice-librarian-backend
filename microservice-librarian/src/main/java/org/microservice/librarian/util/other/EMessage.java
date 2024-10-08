package org.microservice.librarian.util.other;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EMessage {
    ERROR_VALIDATION("Validation failed"),
    SUCCESSFUL("Successful operation"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    RESOURCE_NOT_FOUND("Resource not found"),
    BAD_REQUEST("Bad request"),
    ERROR_GENERIC("An error has occurred"),
    ;

    private final String message;

    EMessage(String message) {
        this.message = message;
    }

    @JsonValue
    public String getMessage() {
        return message;
    }
}
