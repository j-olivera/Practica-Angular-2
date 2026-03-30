package com.gestor.game.application.usecase.game;

import com.gestor.game.application.dto.game.GameResponse;
import com.gestor.game.application.mappers.game.GameMapper;
import com.gestor.game.application.port.out.game.GameRepositoryPort;
import com.gestor.game.core.entities.game.Game;
import com.gestor.game.core.exceptions.game.GameDoesNotExistException;
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
class RetrieveGameHistoryUseCaseImplTest {

    @Mock
    private GameRepositoryPort gameRepositoryPort;

    @Mock
    private GameMapper gameMapper;

    @InjectMocks
    private RetrieveGameHistoryUseCaseImpl retrieveGameHistoryUseCase;

    @Test
    void getGameById_ExistingId_ReturnsGame() {
        // Arrange
        Long gameId = 1L;
        Game gameEntity = mock(Game.class);
        GameResponse expectedResponse = mock(GameResponse.class);

        when(gameRepositoryPort.getGameById(gameId)).thenReturn(Optional.of(gameEntity));
        when(gameMapper.toResponse(gameEntity)).thenReturn(expectedResponse);

        // Act
        GameResponse response = retrieveGameHistoryUseCase.getGameById(gameId);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    void getGameById_NonExistingId_ThrowsException() {
        // Arrange
        Long gameId = 99L;
        when(gameRepositoryPort.getGameById(gameId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(GameDoesNotExistException.class, () -> retrieveGameHistoryUseCase.getGameById(gameId));
    }

    @Test
    void getGameByUserId_ExistingGames_ReturnsList() {
        // Arrange
        Long userId = 1L;
        List<Game> games = List.of(mock(Game.class));
        List<GameResponse> expectedResponse = List.of(mock(GameResponse.class));

        when(gameRepositoryPort.findByUserId(userId)).thenReturn(games);
        when(gameMapper.toResponse(games)).thenReturn(expectedResponse);

        // Act
        List<GameResponse> response = retrieveGameHistoryUseCase.getGameByUserId(userId);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    void getGameByUserId_NoGames_ThrowsException() {
        // Arrange
        Long userId = 1L;
        when(gameRepositoryPort.findByUserId(userId)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(GameDoesNotExistException.class, () -> retrieveGameHistoryUseCase.getGameByUserId(userId));
    }
}
