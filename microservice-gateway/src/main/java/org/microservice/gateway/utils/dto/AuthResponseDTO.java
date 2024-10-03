package org.microservice.gateway.utils.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@Builder
@JsonPropertyOrder({"username", "message", "status", "jwt"})
public record AuthResponseDTO(
        String username,
        String message,
        String jwt,
        Boolean status) {
}