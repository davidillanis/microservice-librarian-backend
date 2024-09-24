package org.microservice.users.utils.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.microservice.users.utils.other.ERole;

import java.util.List;

public record AuthCreateUserRequestDTO(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String usuaNomb,
        @NotBlank String usuaApelPaterno,
        @NotBlank String usuaApelMaterno,
        @NotBlank String usuaDNI,
        String usuaTele,
        @NotNull List<ERole> roleList
) {
}
