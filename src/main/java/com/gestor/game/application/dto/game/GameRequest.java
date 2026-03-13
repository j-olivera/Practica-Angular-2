package com.gestor.game.application.dto.game;

import com.gestor.game.core.enums.game.Result;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record GameRequest(
        Long userId,
        Result gameResult
) {
}
