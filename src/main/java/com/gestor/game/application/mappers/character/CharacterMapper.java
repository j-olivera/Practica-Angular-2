package com.gestor.game.application.mappers.character;

import com.gestor.game.application.dto.character.CharacterRequest;
import com.gestor.game.application.dto.character.CharacterResponse;
import com.gestor.game.application.mappers.build.BuildMapper;
import com.gestor.game.core.entities.build.Build;
import com.gestor.game.core.entities.user.User;
import com.gestor.game.core.entities.character.Character;
import java.util.List;

public class CharacterMapper {

    private final BuildMapper buildMapper;

    public CharacterMapper(BuildMapper buildMapper) {
        this.buildMapper = buildMapper;
    }

    // Recibe las entidades que el CharacterUseCase buscó previamente
    public Character toEntity(CharacterRequest request, User user, Build build) {
        return Character.create(user, request.name(), build, request.warriorClass());
    }

    public CharacterResponse toResponse(Character character) {
        return new CharacterResponse(
                character.getId(),
                character.getUser().getId(), // Tu DTO pedía solo el Long userId
                character.getName(),
                buildMapper.toResponse(character.getBuild()), // Usamos el mapper inyectado
                character.getWarriorClass()
        );
    }

    public List<CharacterResponse> toResponse(List<Character> characters) {
        return characters.stream().map(this::toResponse).toList();
    }

}
