package com.gestor.game.application.dto.character;

import com.gestor.game.application.dto.build.BuildResponse;
import com.gestor.game.core.enums.character.WarriorClass;

public record CharacterResponse(
        Long id,
        Long userId, //puedo cambiarlo a UserResponse si luego quiero ver datos en el front
        String name,
        BuildResponse build,
        WarriorClass warriorClass
) {
}
