package com.gestor.game.application.usecase.character;

import com.gestor.game.application.dto.character.CharacterResponse;
import com.gestor.game.application.mappers.character.CharacterMapper;
import com.gestor.game.application.port.out.build.BuildRepositoryPort;
import com.gestor.game.application.port.out.character.CharacterRepositoryPort;
import com.gestor.game.core.entities.build.Build;
import com.gestor.game.core.entities.character.Character;
import com.gestor.game.core.entities.user.User;
import com.gestor.game.core.enums.character.WarriorClass;
import com.gestor.game.core.exceptions.build.BuildDontExistException;
import com.gestor.game.core.exceptions.character.CharacterDoesNotExistException;
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
class UpdateCharacterBuildUseCaseImplTest {

    @Mock
    private CharacterRepositoryPort characterRepositoryPort;

    @Mock
    private BuildRepositoryPort buildRepositoryPort;

    @Mock
    private CharacterMapper characterMapper;

    @InjectMocks
    private UpdateCharacterBuildUseCaseImpl updateCharacterBuildUseCase;

    @Test
    void updateCharacterBuild_ValidData_ReturnsCharacterResponse() {
        // Arrange
        Long characterId = 1L;
        Long buildId = 2L;

        Build build = mock(Build.class);
        User user = mock(User.class);
        
        Character originalCharacter = Character.reconstruct(characterId, user, "Heroe", mock(Build.class), WarriorClass.GUERRERO);
        Character updatedCharacter = mock(Character.class); // Representation of the newly created object
        CharacterResponse expectedResponse = mock(CharacterResponse.class);

        when(buildRepositoryPort.findById(buildId)).thenReturn(Optional.of(build));
        when(characterRepositoryPort.findById(characterId)).thenReturn(Optional.of(originalCharacter));

        when(characterRepositoryPort.save(any(Character.class))).thenReturn(updatedCharacter);
        when(characterMapper.toResponse(updatedCharacter)).thenReturn(expectedResponse);

        // Act
        CharacterResponse response = updateCharacterBuildUseCase.updateCharacterBuild(characterId, buildId);

        // Assert
        assertEquals(expectedResponse, response);
        verify(characterRepositoryPort).save(any(Character.class));
    }

    @Test
    void updateCharacterBuild_BuildNotFound_ThrowsException() {
        // Arrange
        Long characterId = 1L;
        Long buildId = 99L;
        when(buildRepositoryPort.findById(buildId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BuildDontExistException.class, () -> updateCharacterBuildUseCase.updateCharacterBuild(characterId, buildId));
        verify(characterRepositoryPort, never()).save(any());
    }

    @Test
    void updateCharacterBuild_CharacterNotFound_ThrowsException() {
        // Arrange
        Long characterId = 99L;
        Long buildId = 2L;
        Build build = mock(Build.class);

        when(buildRepositoryPort.findById(buildId)).thenReturn(Optional.of(build));
        when(characterRepositoryPort.findById(characterId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CharacterDoesNotExistException.class, () -> updateCharacterBuildUseCase.updateCharacterBuild(characterId, buildId));
        verify(characterRepositoryPort, never()).save(any());
    }
}
