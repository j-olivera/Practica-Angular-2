package com.gestor.game.core.exceptions.user;

public class UserDontExistException extends RuntimeException {
    public UserDontExistException(String message) {
        super(message);
    }
}
