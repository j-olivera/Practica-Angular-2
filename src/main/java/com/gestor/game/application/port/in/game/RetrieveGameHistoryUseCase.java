package com.gestor.game.application.port.in.game;

import com.gestor.game.application.dto.game.GameResponse;

import java.util.List;

public interface RetrieveGameHistoryUseCase {
    GameResponse getGameById(Long id);
    List<GameResponse> getGameByUserId(Long userId);
}
