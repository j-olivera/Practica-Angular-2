package com.gestor.game.application.dto.user;

public record UserRequest(
        String name,
        String email,
        String password
) {}
