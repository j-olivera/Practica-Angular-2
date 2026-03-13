package com.gestor.game.core.exceptions.game;

public class GameDoesNotExistException extends RuntimeException {
    public GameDoesNotExistException(String message) {
        super(message);
    }
}
