package io.github.fabiocintra.login.model.dto.token;

public record TokenResponse(
        String access_token,
        String refresh_token,
        String token_type,
        Integer expires_in,
        String scope
) {
}