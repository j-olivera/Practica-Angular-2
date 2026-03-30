package com.gestor.game.infrastructure.adapters.persistence.build;

import com.gestor.game.core.entities.build.Build;
import com.gestor.game.core.entities.item.Item;
import com.gestor.game.core.enums.item.Category;
import com.gestor.game.infrastructure.adapters.persistence.item.ItemEntity;
import com.gestor.game.infrastructure.adapters.persistence.item.ItemRepositoryJpa;
import com.gestor.game.infrastructure.adapters.persistence.item.ItemMapperJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({BuildAdapterJpa.class, BuildMapperJpa.class, ItemMapperJpa.class})
class BuildAdapterJpaTest {

    @Autowired
    private BuildAdapterJpa buildAdapterJpa;

    @Autowired
    private BuildRepositoryJpa buildRepositoryJpa;

    @Autowired
    private ItemRepositoryJpa itemRepositoryJpa;

    @Autowired
    private ItemMapperJpa itemMapperJpa;

    private ItemEntity swordEntity;
    private ItemEntity armorEntity;
    private ItemEntity mountEntity;
    private ItemEntity blessingEntity;

    @BeforeEach
    void setUp() {
        swordEntity = itemRepositoryJpa.save(new ItemEntity(null, "Sword", 10, Category.ESPADA));
        armorEntity = itemRepositoryJpa.save(new ItemEntity(null, "Armor", 20, Category.ARMADURA));
        mountEntity = itemRepositoryJpa.save(new ItemEntity(null, "Mount", 30, Category.MONTURA));
        blessingEntity = itemRepositoryJpa.save(new ItemEntity(null, "Blessing", 40, Category.BENDICION));
    }

    @Test
    void save_ValidBuild_SavesToDatabase() {
        // Arrange
        Item sword = itemMapperJpa.toCoreEntity(swordEntity);
        Item armor = itemMapperJpa.toCoreEntity(armorEntity);
        Item mount = itemMapperJpa.toCoreEntity(mountEntity);
        Item blessing = itemMapperJpa.toCoreEntity(blessingEntity);
        
        Build build = Build.create("My Build", sword, armor, mount, blessing);

        // Act
        Build savedBuild = buildAdapterJpa.save(build);

        // Assert
        assertNotNull(savedBuild.getId());
        assertEquals("My Build", savedBuild.getName());
        assertEquals(sword.getId(), savedBuild.getSword().getId());

        assertTrue(buildRepositoryJpa.findById(savedBuild.getId()).isPresent());
    }

    @Test
    void findById_ExistingBuild_ReturnsBuild() {
        // Arrange
        BuildEntity entity = new BuildEntity(null, "Test Build", swordEntity, armorEntity, mountEntity, blessingEntity);
        entity = buildRepositoryJpa.save(entity);

        // Act
        Optional<Build> found = buildAdapterJpa.findById(entity.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Test Build", found.get().getName());
        assertEquals(swordEntity.getId(), found.get().getSword().getId());
    }

    @Test
    void findByName_ExistingBuild_ReturnsBuild() {
        // Arrange
        BuildEntity entity = new BuildEntity(null, "Unique Name", swordEntity, armorEntity, mountEntity, blessingEntity);
        buildRepositoryJpa.save(entity);

        // Act
        Optional<Build> found = buildAdapterJpa.findByName("Unique Name");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Unique Name", found.get().getName());
    }

    @Test
    void findAll_MultipleBuilds_ReturnsList() {
        // Arrange
        buildRepositoryJpa.save(new BuildEntity(null, "B1", swordEntity, armorEntity, mountEntity, blessingEntity));
        buildRepositoryJpa.save(new BuildEntity(null, "B2", swordEntity, armorEntity, mountEntity, blessingEntity));

        // Act
        List<Build> builds = buildAdapterJpa.getAllBuilds();

        // Assert
        assertFalse(builds.isEmpty());
        assertTrue(builds.size() >= 2);
    }

    @Test
    void delete_ExistingBuild_DeletesFromDatabase() {
        // Arrange
        BuildEntity entity = new BuildEntity(null, "To Delete", swordEntity, armorEntity, mountEntity, blessingEntity);
        entity = buildRepositoryJpa.save(entity);

        // Act
        buildAdapterJpa.delete(entity.getId());

        // Assert
        assertFalse(buildRepositoryJpa.findById(entity.getId()).isPresent());
    }
}
