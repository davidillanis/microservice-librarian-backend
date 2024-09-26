package util.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AuthCreateUserRequestDTO(@NotBlank String username,
                                       @NotBlank String password,
                                       @NotBlank String usuaNomb,
                                       @NotBlank String usuaApelPaterno,
                                       @NotBlank String usuaApelMaterno,
                                       @NotBlank String usuaDNI,
                                       String usuaTele,
                                       @NotNull List<String> roleListName) {
}