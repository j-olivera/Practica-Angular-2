package com.gestor.game.application.port.in.game;

import com.gestor.game.application.dto.game.GameResponse;

import java.util.List;

public interface RetrieveGameHistoryUseCase {
    GameResponse getGameById(Long id, Long requesterUserId);

    List<GameResponse> getGameByUserId(Long userId, Long requesterUserId);
}
