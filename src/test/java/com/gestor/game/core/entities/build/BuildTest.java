package com.gestor.game.core.entities.build;

import com.gestor.game.core.entities.item.Item;
import com.gestor.game.core.enums.item.Category;
import com.gestor.game.core.exceptions.NameNotValidException;
import com.gestor.game.core.exceptions.NullException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildTest {

    private Item createValidItem(Category category) {
        return Item.create("TestItem", 10, category);
    }

    @Test
    void create_ValidData_CreatesBuild() {
        Item sword = createValidItem(Category.ESPADA);
        Item armor = createValidItem(Category.ARMADURA);
        Item mount = createValidItem(Category.MONTURA);
        Item blessing = createValidItem(Category.BENDICION);

        Build build = Build.create("Build Suprema", sword, armor, mount, blessing);
        
        assertNotNull(build);
        assertNull(build.getId());
        assertEquals("Build Suprema", build.getName());
        assertEquals(sword, build.getSword());
        assertEquals(armor, build.getArmor());
        assertEquals(mount, build.getMount());
        assertEquals(blessing, build.getBlessing());
    }

    @Test
    void reconstruct_ValidData_ReconstructsBuild() {
        Item sword = createValidItem(Category.ESPADA);
        Item armor = createValidItem(Category.ARMADURA);
        Item mount = createValidItem(Category.MONTURA);
        Item blessing = createValidItem(Category.BENDICION);

        Build build = Build.reconstruct(1L, "Build Basica", sword, armor, mount, blessing);
        
        assertNotNull(build);
        assertEquals(1L, build.getId());
        assertEquals("Build Basica", build.getName());
    }

    @Test
    void validate_NullValues_ThrowsNullException() {
        Item item = createValidItem(Category.ESPADA);
        assertThrows(NullException.class, () -> Build.create(null, item, item, item, item));
        assertThrows(NullException.class, () -> Build.create("Name", null, item, item, item));
        assertThrows(NullException.class, () -> Build.create("Name", item, null, item, item));
        assertThrows(NullException.class, () -> Build.create("Name", item, item, null, item));
        assertThrows(NullException.class, () -> Build.create("Name", item, item, item, null));
    }

    @Test
    void validate_InvalidNameLength_ThrowsNameNotValidException() {
        Item item = createValidItem(Category.ESPADA);
        assertThrows(NameNotValidException.class, () -> Build.create("Ab", item, item, item, item));
        assertThrows(NameNotValidException.class, () -> Build.create("UnNombreExcesivamenteLargoAqui", item, item, item, item));
        assertThrows(NameNotValidException.class, () -> Build.create("   ", item, item, item, item));
    }
}
