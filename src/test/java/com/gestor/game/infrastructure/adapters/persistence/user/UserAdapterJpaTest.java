package com.gestor.game.infrastructure.adapters.persistence.user;

import com.gestor.game.core.entities.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({UserAdapterJpa.class, UserMapperJpa.class})
class UserAdapterJpaTest {

    @Autowired
    private UserAdapterJpa userAdapterJpa;

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Test
    void save_ValidUser_SavesToDatabase() {
        // Arrange
        User user = User.create("Juan", "test@test.com", "pass");

        // Act
        User savedUser = userAdapterJpa.save(user);

        // Assert
        assertNotNull(savedUser.getId());
        assertEquals("Juan", savedUser.getName());
        assertEquals("test@test.com", savedUser.getEmail());
        
        assertTrue(userRepositoryJpa.findById(savedUser.getId()).isPresent());
    }

    @Test
    void findById_ExistingUser_ReturnsUser() {
        // Arrange
        UserEntity entity = new UserEntity(null, "Pedro", "pedro@test.com", "pass");
        entity = userRepositoryJpa.save(entity);

        // Act
        Optional<User> found = userAdapterJpa.findById(entity.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Pedro", found.get().getName());
    }

    @Test
    void findByEmail_ExistingUser_ReturnsUser() {
        // Arrange
        UserEntity entity = new UserEntity(null, "Maria", "maria@test.com", "pass");
        userRepositoryJpa.save(entity);

        // Act
        Optional<User> found = userAdapterJpa.findByEmail("maria@test.com");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Maria", found.get().getName());
    }

    @Test
    void existsByEmail_ExistingUser_ReturnsTrue() {
        // Arrange
        UserEntity entity = new UserEntity(null, "Jose", "jose@test.com", "pass");
        userRepositoryJpa.save(entity);

        // Act
        boolean exists = userAdapterJpa.existsByEmail("jose@test.com");

        // Assert
        assertTrue(exists);
    }
}
