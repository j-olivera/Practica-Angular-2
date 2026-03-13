package com.gestor.game.application.dto.game;

import com.gestor.game.core.enums.game.Result;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record GameResponse(
        Long id,
        Long userId,
        LocalDate date,
        Result gameResult
) { }
