package com.gestor.game.infrastructure.adapters.web.character;

import com.gestor.game.application.dto.character.CharacterRequest;
import com.gestor.game.application.dto.character.CharacterResponse;
import com.gestor.game.application.port.in.character.CreateCharacterUseCase;
import com.gestor.game.application.port.in.character.DeleteCharacterUseCase;
import com.gestor.game.application.port.in.character.RetrieveCharacterUseCase;
import com.gestor.game.application.port.in.character.UpdateCharacterBuildUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirement(name = "bearerAuth")
public class CharacterController {

    private final CreateCharacterUseCase createCharacterUseCase;
    private final DeleteCharacterUseCase deleteCharacterUseCase;
    private final RetrieveCharacterUseCase retrieveCharacterUseCase;
    private final UpdateCharacterBuildUseCase updateCharacterBuildUseCase;

    public CharacterController(CreateCharacterUseCase createCharacterUseCase, DeleteCharacterUseCase deleteCharacterUseCase, RetrieveCharacterUseCase retrieveCharacterUseCase, UpdateCharacterBuildUseCase updateCharacterBuildUseCase) {
        this.createCharacterUseCase = createCharacterUseCase;
        this.deleteCharacterUseCase = deleteCharacterUseCase;
        this.retrieveCharacterUseCase = retrieveCharacterUseCase;
        this.updateCharacterBuildUseCase = updateCharacterBuildUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<CharacterResponse> createCharacter(
            @RequestBody CharacterRequest characterRequest,
            @AuthenticationPrincipal Long authenticatedUserId) {
        CharacterResponse characterResponse =
                createCharacterUseCase.createCharacter(characterRequest, authenticatedUserId);
        return new ResponseEntity<>(characterResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponse> getCharacterById(
            @PathVariable Long id,
            @AuthenticationPrincipal Long authenticatedUserId) {
        CharacterResponse characterResponse =
                retrieveCharacterUseCase.getCharacterById(id, authenticatedUserId);
        return new ResponseEntity<>(characterResponse, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CharacterResponse>> getCharactersByUserId(
            @PathVariable Long userId,
            @AuthenticationPrincipal Long authenticatedUserId) {
        List<CharacterResponse> list =
                retrieveCharacterUseCase.getCharactersByUserId(userId, authenticatedUserId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/{characterId}/build/{buildId}")
    public ResponseEntity<CharacterResponse> updateCharacter(
            @PathVariable Long characterId,
            @PathVariable Long buildId,
            @AuthenticationPrincipal Long authenticatedUserId) {
        CharacterResponse characterResponse = updateCharacterBuildUseCase.updateCharacterBuild(
                characterId, buildId, authenticatedUserId);
        return new ResponseEntity<>(characterResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCharacter(
            @PathVariable Long id,
            @AuthenticationPrincipal Long authenticatedUserId) {
        deleteCharacterUseCase.deleteCharacter(id, authenticatedUserId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
