package io.github.fabiocintra.login.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequest(
        @NotBlank(message = "Campo Obrigatorio!")
        @Size(min=3, max = 200, message = "Deve conter de 3 a 200 caracteres!")
        String userName,
        @NotBlank(message = "Campo Obrigatorio!")
        @Size(min=8, max = 100, message = "Deve conter de 8 a 100 caracteres!")
        String password
) {
}
