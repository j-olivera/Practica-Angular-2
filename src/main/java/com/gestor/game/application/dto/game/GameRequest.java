package com.gestor.game.application.dto.game;

import com.gestor.game.core.enums.game.Result;

import java.time.LocalDateTime;

public record GameRequest(
        Long userId,
        LocalDateTime date,
        Result gameResult
) {
}
