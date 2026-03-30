package com.gestor.game.core.entities.user;

import com.gestor.game.core.exceptions.NameNotValidException;
import com.gestor.game.core.exceptions.NullException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void create_ValidData_CreatesUser() {
        User user = User.create("Juan", "juan@test.com", "password123");
        
        assertNotNull(user);
        assertNull(user.getId());
        assertEquals("Juan", user.getName());
        assertEquals("juan@test.com", user.getEmail());
        assertEquals("password123", user.getPassword());
    }

    @Test
    void reconstruct_ValidData_ReconstructsUser() {
        User user = User.reconstruct(1L, "Juan", "juan@test.com", "password123");
        
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("Juan", user.getName());
        assertEquals("juan@test.com", user.getEmail());
        assertEquals("password123", user.getPassword());
    }

    @Test
    void validate_NullOrEmptyValues_ThrowsNullException() {
        assertThrows(NullException.class, () -> User.create(null, "email", "pass"));
        assertThrows(NullException.class, () -> User.create("", "email", "pass"));
        assertThrows(NullException.class, () -> User.create("Juan", null, "pass"));
        assertThrows(NullException.class, () -> User.create("Juan", "", "pass"));
        assertThrows(NullException.class, () -> User.create("Juan", "email", null));
        assertThrows(NullException.class, () -> User.create("Juan", "email", ""));
    }

    @Test
    void validate_NonAsciiName_ThrowsNameNotValidException() {
        assertThrows(NameNotValidException.class, () -> User.create("Juán", "email@test.com", "pass"));
    }
}
