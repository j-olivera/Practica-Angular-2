package com.gestor.game.application.usecase.user;

import com.gestor.game.application.dto.user.UserRequest;
import com.gestor.game.application.dto.user.UserResponse;
import com.gestor.game.application.port.out.user.UserRepositoryPort;
import com.gestor.game.core.entities.user.User;
import com.gestor.game.core.exceptions.user.UserAlreadyExistException;
import com.gestor.game.application.mappers.user.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    @Test
    void createUser_ValidData_ReturnsUserResponse() {
        // Arrange
        UserRequest request = new UserRequest("Juan", "test@test.com", "pass");
        User userEntity = User.create("Juan", "test@test.com", "pass");
        User savedUser = User.reconstruct(1L, "Juan", "test@test.com", "pass");
        UserResponse expectedResponse = new UserResponse(1L, "Juan", "test@test.com");

        when(userRepositoryPort.existsByEmail(request.email())).thenReturn(false);
        when(userMapper.toEntity(request)).thenReturn(userEntity);
        when(userRepositoryPort.save(userEntity)).thenReturn(savedUser);
        when(userMapper.toResponse(savedUser)).thenReturn(expectedResponse);

        // Act
        UserResponse response = createUserUseCase.createUser(request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse.id(), response.id());
        assertEquals(expectedResponse.email(), response.email());
        
        verify(userRepositoryPort).existsByEmail(request.email());
        verify(userRepositoryPort).save(userEntity);
        verify(userMapper).toResponse(savedUser);
    }

    @Test
    void createUser_EmailAlreadyExists_ThrowsException() {
        // Arrange
        UserRequest request = new UserRequest("Juan", "existent@test.com", "pass");
        when(userRepositoryPort.existsByEmail(request.email())).thenReturn(true);

        // Act & Assert
        assertThrows(UserAlreadyExistException.class, () -> createUserUseCase.createUser(request));
        
        verify(userRepositoryPort, never()).save(any());
        verify(userMapper, never()).toEntity(any(UserRequest.class));
    }
}
