package com.gestor.game.core.exceptions.character;

public class CharacterDoesNotExistException extends RuntimeException {
    public CharacterDoesNotExistException(String message) {
        super(message);
    }
}
