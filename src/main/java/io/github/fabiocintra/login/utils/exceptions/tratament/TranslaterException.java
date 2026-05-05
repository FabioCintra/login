package io.github.fabiocintra.login.utils.exceptions.tratament;

import java.util.List;

public record TranslaterException(
        Integer status,
        String menssage,
        List<FieldError> errors
) {
}
