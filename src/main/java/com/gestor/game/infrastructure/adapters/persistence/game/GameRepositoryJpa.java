package com.gestor.game.infrastructure.adapters.persistence.game;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepositoryJpa extends JpaRepository<GameEntity, Long> {
    List<GameEntity> findByUserId(Long id);
}
