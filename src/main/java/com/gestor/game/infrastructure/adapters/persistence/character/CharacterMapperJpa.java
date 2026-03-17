package com.gestor.game.infrastructure.adapters.persistence.character;
import com.gestor.game.core.entities.character.Character;
import com.gestor.game.infrastructure.adapters.persistence.build.BuildMapperJpa;
import com.gestor.game.infrastructure.adapters.persistence.user.UserMapperJpa;
import org.springframework.stereotype.Component;

@Component
public class CharacterMapperJpa {

    private final BuildMapperJpa buildMapperJpa;
    private final UserMapperJpa userMapperJpa;

    public CharacterMapperJpa(BuildMapperJpa buildMapperJpa, UserMapperJpa userMapperJpa) {
        this.buildMapperJpa = buildMapperJpa;
        this.userMapperJpa = userMapperJpa;
    }

    public CharacterEntity toJpaEntity(Character character) {
        if (character == null) return null;
        return new CharacterEntity(
                character.getId(),
                userMapperJpa.toEntity(character.getUser()),
                character.getName(),
                buildMapperJpa.toJpaEntity(character.getBuild()),
                character.getWarriorClass()
        );
    }
    public Character toCoreEntity(CharacterEntity characterEntity) {
        if (characterEntity == null) return null;
        return Character.reconstruct(
                characterEntity.getId(),
                userMapperJpa.toCore(characterEntity.getUserEntity()),
                characterEntity.getName(),
                buildMapperJpa.toCoreEntity(characterEntity.getBuildEntity()),
                characterEntity.getWarriorClass()
        );
    }
}
