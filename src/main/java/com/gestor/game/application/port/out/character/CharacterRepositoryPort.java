package com.gestor.game.application.port.out.character;

import java.util.List;
import java.util.Optional;
import com.gestor.game.core.entities.character.Character;
public interface CharacterRepositoryPort {
    Character save(Character character);
    Optional<Character> findById(Long id);
    List<Character> findByUserId(Long userId);
    //Optional<Character> findByName(String name);
    boolean existsByName(String name);
    void delete(Long id);

}
