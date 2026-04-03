package com.gestor.game.application.port.in.character;

import com.gestor.game.application.dto.character.CharacterResponse;

public interface UpdateCharacterBuildUseCase {
    CharacterResponse updateCharacterBuild(Long characterId, Long buildId, Long requesterUserId);
}
