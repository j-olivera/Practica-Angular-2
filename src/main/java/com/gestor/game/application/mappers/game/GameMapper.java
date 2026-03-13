package com.gestor.game.application.mappers.game;

import com.gestor.game.application.dto.game.GameRequest;
import com.gestor.game.application.dto.game.GameResponse;
import com.gestor.game.core.entities.game.Game;

import java.util.List;

public class GameMapper {
    public Game toEntity(GameRequest request) {
        return Game.create(request.userId(), request.gameResult());
    }

    public GameResponse toResponse(Game game) {
        return new GameResponse(game.getId(), game.getUserId(), game.getDate(), game.getGameResult());
    }

    public List<GameResponse> toResponse(List<Game> games) {
        return games.stream().map(this::toResponse).toList();
    }
}
