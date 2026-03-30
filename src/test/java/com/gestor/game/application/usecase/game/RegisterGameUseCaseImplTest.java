package com.gestor.game.application.usecase.game;

import com.gestor.game.application.dto.game.GameRequest;
import com.gestor.game.application.dto.game.GameResponse;
import com.gestor.game.application.mappers.game.GameMapper;
import com.gestor.game.application.port.out.game.GameRepositoryPort;
import com.gestor.game.core.entities.game.Game;
import com.gestor.game.core.enums.game.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterGameUseCaseImplTest {

    @Mock
    private GameRepositoryPort gameRepositoryPort;

    @Mock
    private GameMapper gameMapper;

    @InjectMocks
    private RegisterGameUseCaseImpl registerGameUseCase;

    @Test
    void registerGame_ValidData_ReturnsGameResponse() {
        // Arrange
        GameRequest request = new GameRequest(1L, Result.COMPLETED);
        Game gameEntity = Game.create(1L, Result.COMPLETED);
        GameResponse expectedResponse = new GameResponse(1L, 1L, LocalDate.now(), Result.COMPLETED);

        when(gameMapper.toEntity(request)).thenReturn(gameEntity);
        when(gameRepositoryPort.save(gameEntity)).thenReturn(gameEntity);
        when(gameMapper.toResponse(gameEntity)).thenReturn(expectedResponse);

        // Act
        GameResponse response = registerGameUseCase.registerGame(request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        
        verify(gameMapper).toEntity(request);
        verify(gameRepositoryPort).save(gameEntity);
        verify(gameMapper).toResponse(gameEntity);
    }
}
