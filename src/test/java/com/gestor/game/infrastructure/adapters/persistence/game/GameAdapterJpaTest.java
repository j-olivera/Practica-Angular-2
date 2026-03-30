package com.gestor.game.infrastructure.adapters.persistence.game;

import com.gestor.game.core.entities.game.Game;
import com.gestor.game.core.enums.game.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({GameAdapterJpa.class, GameMapperJpa.class})
class GameAdapterJpaTest {

    @Autowired
    private GameAdapterJpa gameAdapterJpa;

    @Autowired
    private GameRepositoryJpa gameRepositoryJpa;

    @Test
    void save_ValidGame_SavesToDatabase() {
        // Arrange
        Game game = Game.reconstruct(null, 1L, LocalDate.now(), Result.COMPLETED);

        // Act
        Game savedGame = gameAdapterJpa.save(game);

        // Assert
        assertNotNull(savedGame.getId());
        assertEquals(1L, savedGame.getUserId());
        assertEquals(Result.COMPLETED, savedGame.getGameResult());
        
        assertTrue(gameRepositoryJpa.findById(savedGame.getId()).isPresent());
    }

    @Test
    void getGameById_ExistingGame_ReturnsGame() {
        // Arrange
        GameEntity entity = new GameEntity(null, 2L, LocalDate.now(), Result.CANCELLED);
        entity = gameRepositoryJpa.save(entity);

        // Act
        Optional<Game> found = gameAdapterJpa.getGameById(entity.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals(2L, found.get().getUserId());
        assertEquals(Result.CANCELLED, found.get().getGameResult());
    }

    @Test
    void findByUserId_ExistingGames_ReturnsList() {
        // Arrange
        Long targetUserId = 3L;
        gameRepositoryJpa.save(new GameEntity(null, targetUserId, LocalDate.now(), Result.COMPLETED));
        gameRepositoryJpa.save(new GameEntity(null, targetUserId, LocalDate.now(), Result.INCOMPLETE));
        gameRepositoryJpa.save(new GameEntity(null, 99L, LocalDate.now(), Result.CANCELLED)); // other user

        // Act
        List<Game> games = gameAdapterJpa.findByUserId(targetUserId);

        // Assert
        assertEquals(2, games.size());
        assertTrue(games.stream().allMatch(g -> g.getUserId().equals(targetUserId)));
    }

    @Test
    void delete_ExistingGame_DeletesFromDatabase() {
        // Arrange
        GameEntity entity = new GameEntity(null, 4L, LocalDate.now(), Result.COMPLETED);
        entity = gameRepositoryJpa.save(entity);

        // Act
        gameAdapterJpa.delete(entity.getId());

        // Assert
        assertFalse(gameRepositoryJpa.findById(entity.getId()).isPresent());
    }
}
