package com.gestor.game.core.entities.game;

import com.gestor.game.core.enums.game.Result;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void create_ValidData_CreatesGame() {
        Game game = Game.create(1L, Result.COMPLETED);
        
        assertNotNull(game);
        assertNull(game.getId());
        assertEquals(1L, game.getUserId());
        assertEquals(Result.COMPLETED, game.getGameResult());
        assertEquals(LocalDate.now(), game.getDate());
    }

    @Test
    void reconstruct_ValidData_ReconstructsGame() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        Game game = Game.reconstruct(10L, 2L, date, Result.INCOMPLETE);
        
        assertNotNull(game);
        assertEquals(10L, game.getId());
        assertEquals(2L, game.getUserId());
        assertEquals(date, game.getDate());
        assertEquals(Result.INCOMPLETE, game.getGameResult());
    }
}
