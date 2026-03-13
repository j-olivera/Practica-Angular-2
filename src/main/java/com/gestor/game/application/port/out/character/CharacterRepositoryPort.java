package com.gestor.game.application.port.out.character;

import java.util.Optional;
import com.gestor.game.core.entities.character.Character;
public interface CharacterRepositoryPort {
    Character save(Character character);
    Optional<Character> findById(Long id);
    //Optional<Character> findByName(String name);
    boolean existsByName(String name);
    void delete(Long id);
}
