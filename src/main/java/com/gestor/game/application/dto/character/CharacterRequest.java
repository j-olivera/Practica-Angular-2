package com.gestor.game.application.dto.character;

import com.gestor.game.core.enums.character.WarriorClass;

public record CharacterRequest(
        Long userId,
        String name,
        Long buildId,
        WarriorClass warriorClass
) {
}
