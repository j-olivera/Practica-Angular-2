package com.gestor.game.core.entities.item;

import com.gestor.game.core.enums.item.Category;
import com.gestor.game.core.exceptions.NameNotValidException;
import com.gestor.game.core.exceptions.NullException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void create_ValidData_CreatesItem() {
        Item item = Item.create("Espada Larga", 10, Category.ESPADA);
        
        assertNotNull(item);
        assertNull(item.getId());
        assertEquals("Espada Larga", item.getName());
        assertEquals(10, item.getStatistic_point());
        assertEquals(Category.ESPADA, item.getCategory());
    }

    @Test
    void reconstruct_ValidData_ReconstructsItem() {
        Item item = Item.reconstruct(1L, "Armadura Ligera", 5, Category.ARMADURA);
        
        assertNotNull(item);
        assertEquals(1L, item.getId());
        assertEquals("Armadura Ligera", item.getName());
        assertEquals(5, item.getStatistic_point());
        assertEquals(Category.ARMADURA, item.getCategory());
    }

    @Test
    void validate_NullOrNegativeValues_ThrowsNullException() {
        assertThrows(NullException.class, () -> Item.create(null, 10, Category.ESPADA));
        assertThrows(NullException.class, () -> Item.create("Espada", -1, Category.ESPADA));
        assertThrows(NullException.class, () -> Item.create("Espada", 10, null));
    }

    @Test
    void validate_InvalidNameLength_ThrowsNameNotValidException() {
        assertThrows(NameNotValidException.class, () -> Item.create("Ab", 10, Category.ESPADA));
        assertThrows(NameNotValidException.class, () -> Item.create("Esta espada tiene un nombre exageradamente largo que pasa los cincuenta caracteres", 10, Category.ESPADA));
        assertThrows(NameNotValidException.class, () -> Item.create("   ", 10, Category.ESPADA));
    }
}
