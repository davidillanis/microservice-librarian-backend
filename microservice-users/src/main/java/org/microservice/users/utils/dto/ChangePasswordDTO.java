package org.microservice.users.utils.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangePasswordDTO {
    @NotBlank(message = "This old password is not empty")
    @NotNull(message = "this old password is not null")
    private String oldPassword;

    @NotBlank(message = "This old password is not empty")
    @NotNull(message = "this old password is not null")
    private String newPassword;
}
