package org.microservice.gateway.utils.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequestDTO(@NotBlank String username,
                                  @NotBlank String password) {
}