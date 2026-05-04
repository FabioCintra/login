package io.github.fabiocintra.login.resources.model.dto.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserResponse(
        int id,
        String userName,
        LocalDate dateOfBirth,
        LocalDateTime create_date
) {}
