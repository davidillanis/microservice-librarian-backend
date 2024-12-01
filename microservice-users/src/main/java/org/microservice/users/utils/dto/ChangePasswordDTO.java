package org.microservice.users.utils.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangePasswordDTO {
    @NotBlank(message = "Esta contraseña antigua, está vacía")
    @NotNull(message = "Esta contraseña antigua no es nula")
    private String oldPassword;

    @NotBlank(message = "Esta contraseña antigua, está vacía")
    @NotNull(message = "Esta contraseña antigua no es nula")
    private String newPassword;
}
