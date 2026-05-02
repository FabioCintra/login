package io.github.fabiocintra.login.resources.utils.exceptions.tratament;

public record FieldError(
        String field,
        String error
) {
}
