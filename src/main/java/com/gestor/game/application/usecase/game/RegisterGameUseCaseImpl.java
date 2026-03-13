package com.gestor.game.application.usecase.game;

import com.gestor.game.application.dto.game.GameRequest;
import com.gestor.game.application.dto.game.GameResponse;
import com.gestor.game.application.mappers.game.GameMapper;
import com.gestor.game.application.port.in.game.RegisterGameUseCase;
import com.gestor.game.application.port.out.game.GameRepositoryPort;
import com.gestor.game.core.entities.game.Game;

public class RegisterGameUseCaseImpl implements RegisterGameUseCase {
    private final GameRepositoryPort gameRepositoryPort;
    private final GameMapper gameMapper;

    public RegisterGameUseCaseImpl(GameRepositoryPort gameRepositoryPort, GameMapper gameMapper) {
        this.gameRepositoryPort = gameRepositoryPort;
        this.gameMapper = gameMapper;
    }

    @Override
    public GameResponse registerGame(GameRequest gameRequest) {
        //mismo caso que item, solo que no puede haber juegos repetidos por logica
        Game  game = gameMapper.toEntity(gameRequest);
        return null;
    }
}
