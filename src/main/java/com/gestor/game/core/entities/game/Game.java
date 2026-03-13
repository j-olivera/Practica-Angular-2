package com.gestor.game.core.entities.game;

import com.gestor.game.core.enums.game.Result;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Game {
    private Long id;
    private Long userId;
    private LocalDate date;
    private Result gameResult;

    private Game(Long id,Long userId, LocalDate date, Result gameResult) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.gameResult = gameResult;
    }

    public static Game create(Long userId, Result gameResult) {
        return new Game(null, userId, LocalDate.now(), gameResult); //la fecha se estable en el momento de la creacion
    }

    public static Game reconstruct(Long id,Long userId, LocalDate date, Result gameResult) {
        return new Game(id, userId,date, gameResult);
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Result getGameResult() {
        return gameResult;
    }

    public Long getUserId() {
        return userId;
    }
}
