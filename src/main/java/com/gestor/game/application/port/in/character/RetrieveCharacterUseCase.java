package com.gestor.game.application.port.in.character;

import com.gestor.game.application.dto.character.CharacterResponse;

import java.util.List;

public interface RetrieveCharacterUseCase {
    CharacterResponse getCharacterById(Long id);
    List<CharacterResponse> getCharactersByUserId(Long userId);
}
