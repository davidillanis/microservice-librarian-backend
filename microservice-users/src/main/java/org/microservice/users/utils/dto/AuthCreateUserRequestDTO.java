package org.microservice.users.utils.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.microservice.users.utils.other.ERole;

import java.util.List;

public record AuthCreateUserRequestDTO(
        @Size(min = 4, max = 25, message = "Este tamaño es mínimo 4 y máximo 25.")
        @NotBlank(message = "Este nombre de usuario no está vacío")
        String username,

        @NotBlank(message = "Esta contraseña no está vacía")
        String password,

        @NotBlank(message = "Esta nombre no está vacía")
        String usuaNomb,

        @NotBlank(message = "Este Apellido Padre está vacío")
        String usuaApelPaterno,

        @NotBlank(message = "Este apellido materno está vacía")
        String usuaApelMaterno,

        @Size(min = 8, max = 10, message = "Este DNI no es válido, longitud mínima 8, máxima 10")
        @NotBlank(message = "Este DNI no debe estár en blanco")
        String usuaDNI,

        @Size(min = 9, max = 15, message = "Este teléfono no es válido, longitud mínima 9, máxima 15")
        String usuaTele,

        @NotNull List<ERole> roleList
) {
}
