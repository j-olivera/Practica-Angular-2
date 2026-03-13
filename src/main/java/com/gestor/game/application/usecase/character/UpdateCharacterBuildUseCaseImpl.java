package com.gestor.game.application.usecase.character;

import com.gestor.game.application.dto.character.CharacterResponse;
import com.gestor.game.application.mappers.character.CharacterMapper;
import com.gestor.game.application.port.in.character.UpdateCharacterBuildUseCase;
import com.gestor.game.application.port.out.build.BuildRepositoryPort;
import com.gestor.game.application.port.out.character.CharacterRepositoryPort;
import com.gestor.game.core.entities.build.Build;
import com.gestor.game.core.entities.character.Character;
import com.gestor.game.core.exceptions.build.BuildDontExistException;
import com.gestor.game.core.exceptions.character.CharacterDoesNotExistException;

public class UpdateCharacterBuildUseCaseImpl implements UpdateCharacterBuildUseCase {
    private final CharacterRepositoryPort characterRepositoryPort;
    private final BuildRepositoryPort buildRepositoryPort;
    private final CharacterMapper characterMapper;

    public UpdateCharacterBuildUseCaseImpl(CharacterRepositoryPort characterRepositoryPort, BuildRepositoryPort buildRepositoryPort, CharacterMapper characterMapper) {
        this.characterRepositoryPort = characterRepositoryPort;
        this.buildRepositoryPort = buildRepositoryPort;
        this.characterMapper = characterMapper;
    }

    @Override
    public CharacterResponse updateCharacterBuild(Long characterId, Long buildId) {
        //revisar si la build existe
        Build build = buildRepositoryPort.findById(buildId).orElseThrow(()-> new BuildDontExistException("Build does not exist"));
        //revisar si el character a actualizar existe
        Character character = characterRepositoryPort.findById(characterId).orElseThrow(()-> new CharacterDoesNotExistException("Character does not exist"));
        //
        Character characterToUpdate = Character.reconstruct(character.getId(),character.getUser(),character.getName(),build,character.getWarriorClass());
        //
        Character saved = characterRepositoryPort.save(characterToUpdate);
        return characterMapper.toResponse(saved);
    }
}
