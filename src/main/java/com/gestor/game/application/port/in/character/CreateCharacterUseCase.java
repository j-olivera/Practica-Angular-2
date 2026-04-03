package com.gestor.game.application.port.in.character;

import com.gestor.game.application.dto.character.CharacterRequest;
import com.gestor.game.application.dto.character.CharacterResponse;

public interface CreateCharacterUseCase {
    CharacterResponse createCharacter(CharacterRequest characterRequest, Long requesterUserId);
}
