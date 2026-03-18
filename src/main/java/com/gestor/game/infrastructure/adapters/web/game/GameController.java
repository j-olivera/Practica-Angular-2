package com.gestor.game.infrastructure.adapters.web.game;

import com.gestor.game.application.dto.game.GameRequest;
import com.gestor.game.application.dto.game.GameResponse;
import com.gestor.game.application.port.in.game.RegisterGameUseCase;
import com.gestor.game.application.port.in.game.RetrieveGameHistoryUseCase;
import com.gestor.game.application.port.out.game.GameRepositoryPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "http://localhost:4200")
public class GameController {

    private final RegisterGameUseCase registerGameUseCase;
    private final RetrieveGameHistoryUseCase retrieveGameHistoryUseCase;

    public GameController(RegisterGameUseCase registerGameUseCase, RetrieveGameHistoryUseCase retrieveGameHistoryUseCase) {
        this.registerGameUseCase = registerGameUseCase;
        this.retrieveGameHistoryUseCase = retrieveGameHistoryUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<GameResponse> createGame(@RequestBody GameRequest gameRequest){
        GameResponse response = registerGameUseCase.registerGame(gameRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> getGameById(@PathVariable Long id){
        GameResponse response = retrieveGameHistoryUseCase.getGameById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<GameResponse>> getGameUserById(@PathVariable Long id){
        List<GameResponse> response = retrieveGameHistoryUseCase.getGameByUserId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
