package com.gestor.game.core.entities.character;

import com.gestor.game.core.entities.build.Build;
import com.gestor.game.core.entities.item.Item;
import com.gestor.game.core.entities.user.User;
import com.gestor.game.core.enums.character.WarriorClass;
import com.gestor.game.core.enums.item.Category;
import com.gestor.game.core.exceptions.NameNotValidException;
import com.gestor.game.core.exceptions.NullException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    private User createValidUser() {
        return User.create("TestUser", "test@test.com", "pass");
    }

    private Build createValidBuild() {
        Item sword = Item.create("TestSword", 10, Category.ESPADA);
        return Build.create("TestBuild", sword, sword, sword, sword);
    }

    @Test
    void create_ValidData_CreatesCharacter() {
        User user = createValidUser();
        Build build = createValidBuild();

        Character character = Character.create(user, "SuperHeroe", build, WarriorClass.GUERRERO);
        
        assertNotNull(character);
        assertNull(character.getId());
        assertEquals(user, character.getUser());
        assertEquals("SuperHeroe", character.getName());
        assertEquals(build, character.getBuild());
        assertEquals(WarriorClass.GUERRERO, character.getWarriorClass());
    }

    @Test
    void reconstruct_ValidData_ReconstructsCharacter() {
        User user = createValidUser();
        Build build = createValidBuild();

        Character character = Character.reconstruct(1L, user, "SuperHeroe", build, WarriorClass.PALADIN);
        
        assertNotNull(character);
        assertEquals(1L, character.getId());
        assertEquals("SuperHeroe", character.getName());
        assertEquals(WarriorClass.PALADIN, character.getWarriorClass());
    }

    @Test
    void validate_NullValues_ThrowsNullException() {
        User user = createValidUser();
        Build build = createValidBuild();

        assertThrows(NullException.class, () -> Character.create(null, "Name", build, WarriorClass.GUERRERO));
        assertThrows(NullException.class, () -> Character.create(user, null, build, WarriorClass.GUERRERO));
        assertThrows(NullException.class, () -> Character.create(user, "Name", null, WarriorClass.GUERRERO));
        assertThrows(NullException.class, () -> Character.create(user, "Name", build, null));
    }

    @Test
    void validate_InvalidNameLength_ThrowsNameNotValidException() {
        User user = createValidUser();
        Build build = createValidBuild();

        assertThrows(NameNotValidException.class, () -> Character.create(user, "Ab", build, WarriorClass.GUERRERO));
        assertThrows(NameNotValidException.class, () -> Character.create(user, "UnNombreDePersonajeExageradamenteLargoQueSupera", build, WarriorClass.GUERRERO));
        assertThrows(NameNotValidException.class, () -> Character.create(user, "   ", build, WarriorClass.GUERRERO));
    }
}
