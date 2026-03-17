package com.gestor.game.infrastructure.adapters.persistence.character;

import com.gestor.game.core.entities.character.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepositoryJpa extends JpaRepository<CharacterEntity,Long> {
    List<Character> findByUserEntityId(Long userEntityId);

    boolean existsByName(String name);
}
