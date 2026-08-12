package com.ponntrix.admin.userservice.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDto(
    @NotBlank(message = "Username or email is required")
    String username,

    @NotBlank(message = "Password is required")
    String password
) {}

