package com.gestor.game.infrastructure.adapters.persistence.character;

import com.gestor.game.core.entities.character.Character;
import com.gestor.game.core.enums.character.WarriorClass;
import com.gestor.game.core.enums.item.Category;
import com.gestor.game.infrastructure.adapters.persistence.build.BuildEntity;
import com.gestor.game.infrastructure.adapters.persistence.build.BuildMapperJpa;
import com.gestor.game.infrastructure.adapters.persistence.build.BuildRepositoryJpa;
import com.gestor.game.infrastructure.adapters.persistence.item.ItemEntity;
import com.gestor.game.infrastructure.adapters.persistence.item.ItemMapperJpa;
import com.gestor.game.infrastructure.adapters.persistence.item.ItemRepositoryJpa;
import com.gestor.game.infrastructure.adapters.persistence.user.UserEntity;
import com.gestor.game.infrastructure.adapters.persistence.user.UserMapperJpa;
import com.gestor.game.infrastructure.adapters.persistence.user.UserRepositoryJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({CharacterAdapterJpa.class, CharacterMapperJpa.class, BuildMapperJpa.class, ItemMapperJpa.class, UserMapperJpa.class})
class CharacterAdapterJpaTest {

    @Autowired
    private CharacterAdapterJpa characterAdapterJpa;

    @Autowired
    private CharacterRepositoryJpa characterRepositoryJpa;

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Autowired
    private BuildRepositoryJpa buildRepositoryJpa;

    @Autowired
    private ItemRepositoryJpa itemRepositoryJpa;

    @Autowired
    private CharacterMapperJpa characterMapperJpa;

    private UserEntity userEntity;
    private BuildEntity buildEntity;

    @BeforeEach
    void setUp() {
        userEntity = userRepositoryJpa.save(new UserEntity(null, "Guerrero", "g@test.com", "pass"));

        ItemEntity sword = itemRepositoryJpa.save(new ItemEntity(null, "Sword", 10, Category.ESPADA));
        ItemEntity armor = itemRepositoryJpa.save(new ItemEntity(null, "Armor", 20, Category.ARMADURA));
        ItemEntity mount = itemRepositoryJpa.save(new ItemEntity(null, "Mount", 30, Category.MONTURA));
        ItemEntity blessing = itemRepositoryJpa.save(new ItemEntity(null, "Blessing", 40, Category.BENDICION));

        buildEntity = buildRepositoryJpa.save(new BuildEntity(null, "My Build", sword, armor, mount, blessing));
    }

    @Test
    void save_ValidCharacter_SavesToDatabase() {
        // Arrange
        CharacterEntity charEntity = new CharacterEntity(null, userEntity, "Garen", buildEntity, WarriorClass.GUERRERO);
        Character character = characterMapperJpa.toCoreEntity(charEntity);

        // Act
        Character savedCharacter = characterAdapterJpa.save(character);

        // Assert
        assertNotNull(savedCharacter.getId());
        assertEquals("Garen", savedCharacter.getName());
        assertEquals(userEntity.getId(), savedCharacter.getUser().getId());
        assertEquals(WarriorClass.GUERRERO, savedCharacter.getWarriorClass());

        assertTrue(characterRepositoryJpa.findById(savedCharacter.getId()).isPresent());
    }

    @Test
    void findById_ExistingCharacter_ReturnsCharacter() {
        // Arrange
        CharacterEntity entity = new CharacterEntity(null, userEntity, "Darius", buildEntity, WarriorClass.GUERRERO);
        entity = characterRepositoryJpa.save(entity);

        // Act
        Optional<Character> found = characterAdapterJpa.findById(entity.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Darius", found.get().getName());
        assertEquals(buildEntity.getId(), found.get().getBuild().getId());
    }

    @Test
    void findByUserId_ExistingUser_ReturnsList() {
        // Arrange
        UserEntity otherUser = userRepositoryJpa.save(new UserEntity(null, "Other", "o@test.com", "pass"));
        characterRepositoryJpa.save(new CharacterEntity(null, userEntity, "MyChar", buildEntity, WarriorClass.BERSERKER));
        characterRepositoryJpa.save(new CharacterEntity(null, otherUser, "OtherChar", buildEntity, WarriorClass.CABALLERO));

        // Act
        List<Character> userChars = characterAdapterJpa.findByUserId(userEntity.getId());

        // Assert
        assertEquals(1, userChars.size());
        assertEquals("MyChar", userChars.get(0).getName());
    }



    @Test
    void delete_ExistingCharacter_DeletesFromDatabase() {
        // Arrange
        CharacterEntity entity = new CharacterEntity(null, userEntity, "Trash", buildEntity, WarriorClass.BARBARO);
        entity = characterRepositoryJpa.save(entity);

        // Act
        characterAdapterJpa.delete(entity.getId());

        // Assert
        assertFalse(characterRepositoryJpa.findById(entity.getId()).isPresent());
    }

    @Test
    void existsByName_ExistingName_ReturnsTrue() {
        // Arrange
        characterRepositoryJpa.save(new CharacterEntity(null, userEntity, "UniqueName", buildEntity, WarriorClass.PALADIN));

        // Act
        boolean exists = characterAdapterJpa.existsByName("UniqueName");

        // Assert
        assertTrue(exists);
    }
}
