package com.gestor.game.application.dto.user;

public record UserResponse(
        Long id,
        String name,
        String email
) {}
