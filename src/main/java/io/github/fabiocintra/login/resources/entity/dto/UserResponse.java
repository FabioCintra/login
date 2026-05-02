package io.github.fabiocintra.login.resources.entity.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserResponse(
        int id,
        String userName,
        String password,
        LocalDate dateOfBirth,
        LocalDateTime create_date
) {}
