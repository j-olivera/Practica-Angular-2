package com.gestor.game.application.usecase.character;

import com.gestor.game.application.dto.character.CharacterResponse;
import com.gestor.game.application.mappers.character.CharacterMapper;
import com.gestor.game.application.port.out.character.CharacterRepositoryPort;
import com.gestor.game.application.port.out.user.UserRepositoryPort;
import com.gestor.game.core.entities.character.Character;
import com.gestor.game.core.entities.user.User;
import com.gestor.game.core.exceptions.character.CharacterDoesNotExistException;
import com.gestor.game.core.exceptions.user.UserDontExistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrieveCharacterUseCaseImplTest {

    @Mock
    private CharacterRepositoryPort characterRepositoryPort;

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private CharacterMapper characterMapper;

    @InjectMocks
    private RetrieveCharacterUseCaseImpl retrieveCharacterUseCase;

    @Test
    void getCharacterById_ExistingId_ReturnsCharacter() {
        // Arrange
        Long characterId = 1L;
        Character characterEntity = mock(Character.class);
        CharacterResponse expectedResponse = mock(CharacterResponse.class);

        when(characterRepositoryPort.findById(characterId)).thenReturn(Optional.of(characterEntity));
        when(characterMapper.toResponse(characterEntity)).thenReturn(expectedResponse);

        // Act
        CharacterResponse response = retrieveCharacterUseCase.getCharacterById(characterId);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    void getCharacterById_NonExistingId_ThrowsException() {
        // Arrange
        Long characterId = 99L;
        when(characterRepositoryPort.findById(characterId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CharacterDoesNotExistException.class, () -> retrieveCharacterUseCase.getCharacterById(characterId));
    }

    @Test
    void getCharactersByUserId_ExistingUserAndCharacters_ReturnsList() {
        // Arrange
        Long userId = 1L;
        User user = mock(User.class);
        when(user.getId()).thenReturn(userId);

        List<Character> characters = List.of(mock(Character.class));
        List<CharacterResponse> expectedResponse = List.of(mock(CharacterResponse.class));

        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(user));
        when(characterRepositoryPort.findByUserId(userId)).thenReturn(characters);
        when(characterMapper.toResponse(characters)).thenReturn(expectedResponse);

        // Act
        List<CharacterResponse> response = retrieveCharacterUseCase.getCharactersByUserId(userId);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    void getCharactersByUserId_UserNotFound_ThrowsException() {
        // Arrange
        Long userId = 99L;
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserDontExistException.class, () -> retrieveCharacterUseCase.getCharactersByUserId(userId));
    }

    @Test
    void getCharactersByUserId_NoCharactersFound_ThrowsException() {
        // Arrange
        Long userId = 1L;
        User user = mock(User.class);
        when(user.getId()).thenReturn(userId);

        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(user));
        when(characterRepositoryPort.findByUserId(userId)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(CharacterDoesNotExistException.class, () -> retrieveCharacterUseCase.getCharactersByUserId(userId));
    }
}
