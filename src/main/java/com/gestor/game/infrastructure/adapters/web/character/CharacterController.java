package com.gestor.game.infrastructure.adapters.web.character;

import com.gestor.game.application.dto.character.CharacterRequest;
import com.gestor.game.application.dto.character.CharacterResponse;
import com.gestor.game.application.port.in.character.CreateCharacterUseCase;
import com.gestor.game.application.port.in.character.DeleteCharacterUseCase;
import com.gestor.game.application.port.in.character.RetrieveCharacterUseCase;
import com.gestor.game.application.port.in.character.UpdateCharacterBuildUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/characters")
@CrossOrigin(origins = "http://localhost:4200")
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
    public ResponseEntity<CharacterResponse> createCharacter(@RequestBody CharacterRequest characterRequest){
        CharacterResponse characterResponse = createCharacterUseCase.createCharacter(characterRequest);
        return new ResponseEntity<>(characterResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponse> getCharacterById(@PathVariable Long id){
        CharacterResponse characterResponse = retrieveCharacterUseCase.getCharacterById(id);
        return new ResponseEntity<>(characterResponse, HttpStatus.OK);
    }
    @PutMapping("/{characterId}/build/{buildId}")
    public ResponseEntity<CharacterResponse> updateCharacter(@PathVariable("characterId") Long characterId, @PathVariable("buildId") Long buildId){
        CharacterResponse characterResponse = updateCharacterBuildUseCase.updateCharacterBuild(characterId, buildId);
        return new ResponseEntity<>(characterResponse, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CharacterResponse> deleteCharacter(@PathVariable Long id){
        deleteCharacterUseCase.deleteCharacter(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
