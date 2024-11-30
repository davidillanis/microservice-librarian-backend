package org.microservice.users.utils.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthUpdateUserRequestDTO(
        @Size(min = 4, max = 25, message = "this size min 4, max 25")
        @NotBlank(message = "This Username is not empty")
        String username,

        @NotBlank(message = "this Name is empty")
        String usuaNomb,

        @NotBlank(message = "this Last Name Father is empty")
        String usuaApelPaterno,

        @NotBlank(message = "this Last Name Mather is empty")
        String usuaApelMaterno,

        @Size(min = 8, max = 10, message = "this DNI invalid length min 8, max 10")
        @NotBlank(message = "This not blank dni")
        String usuaDNI,

        @Size(min = 9, max = 15, message = "this Telephone invalid length min 9, max 15")
        String usuaTele
) {
}
