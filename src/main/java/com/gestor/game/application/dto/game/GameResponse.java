package com.gestor.game.application.dto.game;

import com.gestor.game.core.enums.game.Result;

import java.time.LocalDateTime;

public record GameResponse(
        Long id,
        Long userId,
        LocalDateTime date,
        Result gameResult
) { }
