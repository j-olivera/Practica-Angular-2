package com.gestor.game.infrastructure.config;

import com.gestor.game.core.exceptions.NameNotValidException;
import com.gestor.game.core.exceptions.NullException;
import com.gestor.game.core.exceptions.build.BuildDontExistException;
import com.gestor.game.core.exceptions.character.CharacterDoesNotExistException;
import com.gestor.game.core.exceptions.game.GameDoesNotExistException;
import com.gestor.game.core.exceptions.item.ItemNotFoundException;
import com.gestor.game.core.exceptions.auth.ForbiddenAccessException;
import com.gestor.game.core.exceptions.auth.InvalidCredentialsException;
import com.gestor.game.core.exceptions.user.UserAlreadyExistException;
import com.gestor.game.core.exceptions.user.UserDontExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 404 NOT FOUND - Cuando buscamos algo y no existe
    @ExceptionHandler({
            UserDontExistException.class,
            ItemNotFoundException.class,
            BuildDontExistException.class,
            CharacterDoesNotExistException.class,
            GameDoesNotExistException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(), // Aquí viaja el mensaje que escribiste en tu Core ("User does not exist")
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // 409 CONFLICT - Cuando intentamos crear algo que ya existe (Ej: Email duplicado)
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleConflictExceptions(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "Forbidden",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    // 400 BAD REQUEST - Cuando los datos enviados son inválidos o nulos
    @ExceptionHandler({NameNotValidException.class, NullException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestExceptions(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Void> handleNoResource(NoResourceFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 500 INTERNAL SERVER ERROR - Para atrapar cualquier error inesperado (Bugs, caída de BD, etc)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ex.printStackTrace();
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Ocurrió un error inesperado en el servidor",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
