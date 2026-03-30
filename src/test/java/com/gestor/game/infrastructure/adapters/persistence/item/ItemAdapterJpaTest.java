package com.gestor.game.infrastructure.adapters.persistence.item;

import com.gestor.game.core.entities.item.Item;
import com.gestor.game.core.enums.item.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({ItemAdapterJpa.class, ItemMapperJpa.class})
class ItemAdapterJpaTest {

    @Autowired
    private ItemAdapterJpa itemAdapterJpa;

    @Autowired
    private ItemRepositoryJpa itemRepositoryJpa;

    @Test
    void save_ValidItem_SavesToDatabase() {
        // Arrange
        Item item = Item.create("Espada", 15, Category.ESPADA);

        // Act
        Item savedItem = itemAdapterJpa.save(item);

        // Assert
        assertNotNull(savedItem.getId());
        assertEquals("Espada", savedItem.getName());
        assertEquals(Category.ESPADA, savedItem.getCategory());
        
        assertTrue(itemRepositoryJpa.findById(savedItem.getId()).isPresent());
    }

    @Test
    void findById_ExistingItem_ReturnsItem() {
        // Arrange
        ItemEntity entity = new ItemEntity(null, "Armadura", 5, Category.ARMADURA);
        entity = itemRepositoryJpa.save(entity);

        // Act
        Optional<Item> found = itemAdapterJpa.findById(entity.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Armadura", found.get().getName());
        assertEquals(Category.ARMADURA, found.get().getCategory());
    }

    @Test
    void findAll_MultipleItems_ReturnsList() {
        // Arrange
        itemRepositoryJpa.save(new ItemEntity(null, "E1", 1, Category.ESPADA));
        itemRepositoryJpa.save(new ItemEntity(null, "E2", 2, Category.ARMADURA));

        // Act
        List<Item> items = itemAdapterJpa.findAll();

        // Assert
        assertFalse(items.isEmpty());
        assertTrue(items.size() >= 2);
    }

    @Test
    void findByCategory_ExistingCategory_ReturnsList() {
        // Arrange
        itemRepositoryJpa.save(new ItemEntity(null, "Montura Real", 20, Category.MONTURA));
        itemRepositoryJpa.save(new ItemEntity(null, "Montura Basica", 10, Category.MONTURA));
        itemRepositoryJpa.save(new ItemEntity(null, "Bendicion", 5, Category.BENDICION));

        // Act
        List<Item> monturas = itemAdapterJpa.findByCategory(Category.MONTURA);

        // Assert
        assertFalse(monturas.isEmpty());
        assertEquals(2, monturas.size());
        assertTrue(monturas.stream().allMatch(i -> i.getCategory() == Category.MONTURA));
    }

    @Test
    void delete_ExistingItem_DeletesFromDatabase() {
        // Arrange
        ItemEntity entity = new ItemEntity(null, "Armadura", 5, Category.ARMADURA);
        entity = itemRepositoryJpa.save(entity);

        // Act
        itemAdapterJpa.delete(entity.getId());

        // Assert
        assertFalse(itemRepositoryJpa.findById(entity.getId()).isPresent());
    }
}
