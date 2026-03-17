package com.gestor.game.infrastructure.adapters.persistence.game;

import com.gestor.game.core.entities.game.Game;

public class GameMapperJpa {
    public GameEntity toJpaEntity(Game game) {
        if (game == null) return null;
        return new GameEntity(
                game.getId(),
                game.getUserId(),
                game.getDate(),
                game.getGameResult()
        );
    }

    public Game toCoreEntity(GameEntity entity) {
        if (entity == null) return null;
        return Game.reconstruct(
                entity.getId(),
                entity.getUserId(),
                entity.getDate(),
                entity.getGameResult()
        );
    }
}
