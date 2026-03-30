package com.gestor.game.application.dto.game;

import com.gestor.game.core.enums.game.Result;


public record GameRequest(
        Long userId,
        Result gameResult
) {
}
