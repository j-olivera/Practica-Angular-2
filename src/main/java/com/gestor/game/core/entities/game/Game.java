package com.gestor.game.core.entities.game;

import com.gestor.game.core.enums.game.Result;

import java.time.LocalDateTime;

public class Game {
    private Long id;
    private Long userId;
    private LocalDateTime date;
    private Result gameResult;

    private Game(Long id,Long userId, LocalDateTime date, Result gameResult) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.gameResult = gameResult;
    }

    public static Game create(Long userId,LocalDateTime date, Result gameResult) {
        return new Game(null, userId,date, gameResult);
    }

    public static Game reconstruct(Long id,Long userId, LocalDateTime date, Result gameResult) {
        return new Game(id, userId,date, gameResult);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Result getGameResult() {
        return gameResult;
    }

    public Long getUserId() {
        return userId;
    }
}
