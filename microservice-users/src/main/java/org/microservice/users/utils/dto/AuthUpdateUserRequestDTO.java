package org.microservice.users.utils.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthUpdateUserRequestDTO(
        @Size(min = 4, max = 25, message = "this size min 4, max 25")
        @NotBlank(message = "Este nombre de usuario no está vacío")
        String username,

        @NotBlank(message = "Este nombre está vacío")
        String usuaNomb,

        @NotBlank(message = "Este Apellido paterno está vacío")
        String usuaApelPaterno,

        @NotBlank(message = "tEste Apellido materno está vacío")
        String usuaApelMaterno,

        @Size(min = 8, max = 10, message = "Este DNI no es válido, longitud mínima 8, máxima 10")
        @NotBlank(message = "Este DNI está en blanco")
        String usuaDNI,

        @Size(min = 9, max = 15, message = "Este teléfono no es válido, longitud mínima 9, máxima 15")
        String usuaTele
) {
}
