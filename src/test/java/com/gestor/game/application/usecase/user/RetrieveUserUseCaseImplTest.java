package com.gestor.game.application.usecase.user;

import com.gestor.game.application.dto.user.UserResponse;
import com.gestor.game.application.port.out.user.UserRepositoryPort;
import com.gestor.game.core.entities.user.User;
import com.gestor.game.core.exceptions.user.UserDontExistException;
import com.gestor.game.application.mappers.user.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrieveUserUseCaseImplTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private RetrieveUserUseCaseImpl retrieveUserUseCase;

    @Test
    void getUserById_ExistingId_ReturnsUserResponse() {
        // Arrange
        Long userId = 1L;
        User userEntity = User.reconstruct(userId, "Juan", "test@test.com", "pass");
        UserResponse expectedResponse = new UserResponse(userId, "Juan", "test@test.com");

        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userMapper.toResponse(userEntity)).thenReturn(expectedResponse);

        // Act
        UserResponse response = retrieveUserUseCase.getUserById(userId);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse.id(), response.id());
        assertEquals(expectedResponse.email(), response.email());
        
        verify(userRepositoryPort).findById(userId);
        verify(userMapper).toResponse(userEntity);
    }

    @Test
    void getUserById_NonExistingId_ThrowsException() {
        // Arrange
        Long userId = 99L;
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserDontExistException.class, () -> retrieveUserUseCase.getUserById(userId));
        
        verify(userMapper, never()).toResponse(any(User.class));
    }
}
