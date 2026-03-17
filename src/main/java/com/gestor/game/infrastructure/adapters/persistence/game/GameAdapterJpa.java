package com.gestor.game.infrastructure.adapters.persistence.game;

import com.gestor.game.application.port.out.game.GameRepositoryPort;
import com.gestor.game.core.entities.game.Game;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class GameAdapterJpa implements GameRepositoryPort {
    private final GameRepositoryJpa gameRepository;
    private final GameMapperJpa gameMapperJpa;

    public GameAdapterJpa(GameRepositoryJpa gameRepository, GameMapperJpa gameMapperJpa) {
        this.gameRepository = gameRepository;
        this.gameMapperJpa = gameMapperJpa;
    }

    @Override
    public Game save(Game game) {
        GameEntity entity = gameMapperJpa.toJpaEntity(game);
        GameEntity savedGameEntity = gameRepository.save(entity);
        return gameMapperJpa.toCoreEntity(savedGameEntity);
    }

    @Override
    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id).map(gameMapperJpa::toCoreEntity);
    }

    @Override
    public List<Game> findByUserId(Long userId) {
        return gameRepository.findByUserId(userId)
                .stream()
                .map(gameMapperJpa::toCoreEntity)
                .toList();
    }

    @Override
    public void delete(Long id) {
        gameRepository.findById(id).ifPresent(gameRepository::delete);
    }
}
