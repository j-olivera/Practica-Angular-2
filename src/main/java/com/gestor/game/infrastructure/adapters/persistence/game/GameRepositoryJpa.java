package com.gestor.game.infrastructure.adapters.persistence.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GameRepositoryJpa extends JpaRepository<GameEntity, Long> {
    List<GameEntity> findByUserId(Long id);
}
