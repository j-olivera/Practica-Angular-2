package com.gestor.game.application.usecase.game;

import com.gestor.game.application.dto.game.GameResponse;
import com.gestor.game.application.mappers.game.GameMapper;
import com.gestor.game.application.port.in.game.RetrieveGameHistoryUseCase;
import com.gestor.game.application.port.out.game.GameRepositoryPort;
import com.gestor.game.core.entities.game.Game;
import com.gestor.game.core.exceptions.game.GameDoesNotExistException;

import java.util.List;

public class RetrieveGameHistoryUseCaseImpl implements RetrieveGameHistoryUseCase {

    private final GameRepositoryPort gameRepositoryPort;
    private final GameMapper gameMapper;

    public RetrieveGameHistoryUseCaseImpl(GameRepositoryPort gameRepositoryPort, GameMapper gameMapper) {
        this.gameRepositoryPort = gameRepositoryPort;
        this.gameMapper = gameMapper;
    }

    @Override
    public GameResponse getGameById(Long id) {
        Game game = gameRepositoryPort.getGameById(id)
                .orElseThrow(()-> new GameDoesNotExistException("Game with id " + id + " does not exist"));
        return gameMapper.toResponse(game);
    }

    @Override
    public List<GameResponse> getGameByUserId(Long userId) {
        List<Game> games = gameRepositoryPort.findByUserId(userId);
        if (games.isEmpty()) {
            throw new GameDoesNotExistException("User with id " + userId + " don't have any games registered");
        }
        return gameMapper.toResponse(games);
    }
}
