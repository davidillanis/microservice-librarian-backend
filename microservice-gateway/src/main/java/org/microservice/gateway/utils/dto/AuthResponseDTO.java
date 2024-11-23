package org.microservice.gateway.utils.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.util.List;

@Builder
@JsonPropertyOrder({"username", "message", "status", "roles", "jwt"})
public record AuthResponseDTO(
        String username,
        String message,
        String jwt,
        List<String> roles,
        Boolean status) {
}