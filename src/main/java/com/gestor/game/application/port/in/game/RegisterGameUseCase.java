package com.gestor.game.application.port.in.game;

import com.gestor.game.application.dto.game.GameRequest;
import com.gestor.game.application.dto.game.GameResponse;

public interface RegisterGameUseCase {
    GameResponse registerGame(GameRequest gameRequest, Long requesterUserId);
}
