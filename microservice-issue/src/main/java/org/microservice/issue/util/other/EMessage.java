package org.microservice.issue.util.other;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EMessage {
    ERROR_VALIDATION("Validation failed"),
    SUCCESSFUL("Successful operation"),
    ERROR_INTERNAL_SERVER("Error internal server"),
    RESOURCE_NOT_FOUND("Resource not found"),
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
