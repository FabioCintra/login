package io.github.fabiocintra.login.utils.exceptions.tratament;

public record FieldError(
        String field,
        String error
) {
}
