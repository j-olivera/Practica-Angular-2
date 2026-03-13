package com.gestor.game.core.exceptions.build;

public class BuildDontExistException extends RuntimeException {
    public BuildDontExistException(String message) {
        super(message);
    }
}
