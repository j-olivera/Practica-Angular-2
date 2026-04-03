package com.gestor.game.application.port.out.security;

import java.util.Optional;

public interface JwtTokenPort {

    String createAccessToken(Long userId, String email);

    Optional<Long> validateAndGetUserId(String token);
}
