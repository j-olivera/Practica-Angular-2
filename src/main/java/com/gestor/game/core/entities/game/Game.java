package com.gestor.game.core.entities.game;

import com.gestor.game.core.enums.game.Result;

import java.time.LocalDateTime;

public class Game {
    private Long id;
    private LocalDateTime date;
    private Result gameResult;

    public Game(Long id, LocalDateTime date, Result gameResult) {
        this.id = id;
        this.date = date;
        this.gameResult = gameResult;
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
}
