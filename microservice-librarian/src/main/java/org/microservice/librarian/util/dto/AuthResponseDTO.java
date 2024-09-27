package org.microservice.librarian.util.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "password", "jwt", "status"})
public record AuthResponseDTO (
        String username,
        String message,
        String jwt,
        boolean status
){
}
