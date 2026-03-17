package com.gestor.game.infrastructure.adapters.persistence.game;

import com.gestor.game.core.enums.game.Result;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "games")
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long userId;
    @Column
    private LocalDate date;
    @Column
    private Result gameResult;

    public GameEntity(Long id, Long userId, LocalDate date, Result gameResult) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.gameResult = gameResult;
    }

    public GameEntity() {}

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public Result getGameResult() {
        return gameResult;
    }
}
