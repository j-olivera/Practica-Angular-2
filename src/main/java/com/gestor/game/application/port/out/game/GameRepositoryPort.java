package com.gestor.game.application.port.out.game;

import com.gestor.game.core.entities.game.Game;

import java.util.List;
import java.util.Optional;

public interface GameRepositoryPort {
    Game save(Game game);
    Optional<Game> getGameById(Long id);
    List<Game> findByUserId(Long userId);
    void delete(Long id);
}
