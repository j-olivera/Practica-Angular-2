package com.gestor.game.application.usecase.character;

import com.gestor.game.application.port.out.character.CharacterRepositoryPort;
import com.gestor.game.core.entities.character.Character;
import com.gestor.game.core.exceptions.character.CharacterDoesNotExistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCharacterUseCaseImplTest {

    @Mock
    private CharacterRepositoryPort characterRepositoryPort;

    @InjectMocks
    private DeleteCharacterUseCaseImpl deleteCharacterUseCase;

    @Test
    void deleteCharacter_ExistingId_DeletesCharacter() {
        // Arrange
        Long characterId = 1L;
        Character characterEntity = mock(Character.class);
        when(characterEntity.getId()).thenReturn(characterId);
        when(characterRepositoryPort.findById(characterId)).thenReturn(Optional.of(characterEntity));

        // Act
        deleteCharacterUseCase.deleteCharacter(characterId);

        // Assert
        verify(characterRepositoryPort).findById(characterId);
        verify(characterRepositoryPort).delete(characterId);
    }

    @Test
    void deleteCharacter_NonExistingId_ThrowsException() {
        // Arrange
        Long characterId = 99L;
        when(characterRepositoryPort.findById(characterId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CharacterDoesNotExistException.class, () -> deleteCharacterUseCase.deleteCharacter(characterId));
        
        verify(characterRepositoryPort, never()).delete(anyLong());
    }
}
