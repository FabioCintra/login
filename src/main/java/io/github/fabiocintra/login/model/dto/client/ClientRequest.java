package io.github.fabiocintra.login.model.dto.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientRequest(
        @NotBlank(message = "Campo Obrigatorio!")
        @Size(min = 10, max = 100, message = "Tamanho invalido!")
        String clientId,
        @NotBlank(message = "Campo Obrigatorio!")
        @Size(min = 10, max = 100, message = "Tamanho invalido!")
        String clientSecret,
        @NotBlank(message = "Campo Obrigatorio!")
        String callbackUrl
) {
}
