package com.gestor.game.application.port.out.character;

import java.util.Optional;

public interface CharacterRepositoryPort {
    Character save(Character character);
    Optional<Character> findById(Long id);
    void delete(Long id);
}
