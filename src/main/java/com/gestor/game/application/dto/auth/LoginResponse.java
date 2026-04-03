package com.gestor.game.application.dto.auth;

import com.gestor.game.application.dto.user.UserResponse;

public record LoginResponse(String accessToken, String tokenType, UserResponse user) {}
