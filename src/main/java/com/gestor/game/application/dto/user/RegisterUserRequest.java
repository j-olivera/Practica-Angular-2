package com.gestor.game.application.dto.user;

public record RegisterUserRequest(
        String name,
        String email,
        String password
) {}
