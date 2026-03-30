package com.gestor.game.application.usecase.character;

import com.gestor.game.application.dto.character.CharacterRequest;
import com.gestor.game.application.dto.character.CharacterResponse;
import com.gestor.game.application.mappers.character.CharacterMapper;
import com.gestor.game.application.port.out.build.BuildRepositoryPort;
import com.gestor.game.application.port.out.character.CharacterRepositoryPort;
import com.gestor.game.application.port.out.user.UserRepositoryPort;
import com.gestor.game.core.entities.build.Build;
import com.gestor.game.core.entities.character.Character;
import com.gestor.game.core.entities.user.User;
import com.gestor.game.core.enums.character.WarriorClass;
import com.gestor.game.core.exceptions.NameNotValidException;
import com.gestor.game.core.exceptions.build.BuildDontExistException;
import com.gestor.game.core.exceptions.user.UserDontExistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCharacterUseCaseImplTest {

    @Mock
    private CharacterRepositoryPort characterRepositoryPort;

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private BuildRepositoryPort buildRepositoryPort;

    @Mock
    private CharacterMapper characterMapper;

    @InjectMocks
    private CreateCharacterUseCaseImpl createCharacterUseCase;

    @Test
    void createCharacter_ValidData_ReturnsCharacterResponse() {
        // Arrange
        CharacterRequest request = new CharacterRequest(1L, "Heroe", 2L, WarriorClass.GUERRERO);
        User user = mock(User.class);
        Build build = mock(Build.class);
        Character characterEntity = mock(Character.class);
        CharacterResponse expectedResponse = mock(CharacterResponse.class);

        when(userRepositoryPort.findById(request.userId())).thenReturn(Optional.of(user));
        when(buildRepositoryPort.findById(request.buildId())).thenReturn(Optional.of(build));
        when(characterRepositoryPort.existsByName(request.name())).thenReturn(false);

        when(characterMapper.toEntity(request, user, build)).thenReturn(characterEntity);
        when(characterRepositoryPort.save(characterEntity)).thenReturn(characterEntity);
        when(characterMapper.toResponse(characterEntity)).thenReturn(expectedResponse);

        // Act
        CharacterResponse response = createCharacterUseCase.createCharacter(request);

        // Assert
        assertEquals(expectedResponse, response);
        verify(characterRepositoryPort).save(characterEntity);
    }

    @Test
    void createCharacter_UserNotFound_ThrowsException() {
        // Arrange
        CharacterRequest request = new CharacterRequest(1L, "Heroe", 2L, WarriorClass.GUERRERO);
        when(userRepositoryPort.findById(request.userId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserDontExistException.class, () -> createCharacterUseCase.createCharacter(request));
    }

    @Test
    void createCharacter_BuildNotFound_ThrowsException() {
        // Arrange
        CharacterRequest request = new CharacterRequest(1L, "Heroe", 2L, WarriorClass.GUERRERO);
        User user = mock(User.class);
        when(userRepositoryPort.findById(request.userId())).thenReturn(Optional.of(user));
        when(buildRepositoryPort.findById(request.buildId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BuildDontExistException.class, () -> createCharacterUseCase.createCharacter(request));
    }

    @Test
    void createCharacter_NameAlreadyExists_ThrowsException() {
        // Arrange
        CharacterRequest request = new CharacterRequest(1L, "Duplicado", 2L, WarriorClass.GUERRERO);
        User user = mock(User.class);
        Build build = mock(Build.class);
        when(userRepositoryPort.findById(request.userId())).thenReturn(Optional.of(user));
        when(buildRepositoryPort.findById(request.buildId())).thenReturn(Optional.of(build));
        when(characterRepositoryPort.existsByName(request.name())).thenReturn(true);

        // Act & Assert
        assertThrows(NameNotValidException.class, () -> createCharacterUseCase.createCharacter(request));
        verify(characterRepositoryPort, never()).save(any());
    }
}
