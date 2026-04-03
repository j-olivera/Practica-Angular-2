package com.gestor.game.application.usecase.game;

import com.gestor.game.application.dto.game.GameRequest;
import com.gestor.game.application.dto.game.GameResponse;
import com.gestor.game.application.mappers.game.GameMapper;
import com.gestor.game.application.port.in.game.RegisterGameUseCase;
import com.gestor.game.application.port.out.game.GameRepositoryPort;
import com.gestor.game.core.entities.game.Game;
import com.gestor.game.core.exceptions.auth.ForbiddenAccessException;

public class RegisterGameUseCaseImpl implements RegisterGameUseCase {
    private final GameRepositoryPort gameRepositoryPort;
    private final GameMapper gameMapper;

    public RegisterGameUseCaseImpl(GameRepositoryPort gameRepositoryPort, GameMapper gameMapper) {
        this.gameRepositoryPort = gameRepositoryPort;
        this.gameMapper = gameMapper;
    }

    @Override
    public GameResponse registerGame(GameRequest gameRequest, Long requesterUserId) {
        if (!gameRequest.userId().equals(requesterUserId)) {
            throw new ForbiddenAccessException("You can only register games for your own account");
        }
        Game  game = gameMapper.toEntity(gameRequest);
        Game saved = gameRepositoryPort.save(game);
        return gameMapper.toResponse(saved);
    }
}
