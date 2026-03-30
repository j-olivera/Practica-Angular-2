package com.gestor.game.infrastructure.adapters.web.character;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestor.game.application.dto.character.CharacterRequest;
import com.gestor.game.application.dto.character.CharacterResponse;
import com.gestor.game.application.port.in.character.CreateCharacterUseCase;
import com.gestor.game.application.port.in.character.DeleteCharacterUseCase;
import com.gestor.game.application.port.in.character.RetrieveCharacterUseCase;
import com.gestor.game.application.port.in.character.UpdateCharacterBuildUseCase;
import com.gestor.game.core.enums.character.WarriorClass;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CharacterController.class)
class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateCharacterUseCase createCharacterUseCase;

    @MockBean
    private DeleteCharacterUseCase deleteCharacterUseCase;

    @MockBean
    private RetrieveCharacterUseCase retrieveCharacterUseCase;

    @MockBean
    private UpdateCharacterBuildUseCase updateCharacterBuildUseCase;

    @Test
    void createCharacter_ValidRequest_ReturnsCreated() throws Exception {
        CharacterRequest request = new CharacterRequest(1L, "Heroe", 2L, WarriorClass.GUERRERO);
        CharacterResponse response = new CharacterResponse(1L, 1L, "Heroe", null, WarriorClass.GUERRERO);

        Mockito.when(createCharacterUseCase.createCharacter(any(CharacterRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/characters/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.name").value(response.name()));
    }

    @Test
    void getCharacterById_ExistingId_ReturnsOk() throws Exception {
        Long id = 1L;
        CharacterResponse response = new CharacterResponse(id, 1L, "Heroe", null, WarriorClass.GUERRERO);

        Mockito.when(retrieveCharacterUseCase.getCharacterById(id)).thenReturn(response);

        mockMvc.perform(get("/api/characters/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()));
    }

    @Test
    void updateCharacter_ValidIds_ReturnsOk() throws Exception {
        Long characterId = 1L;
        Long buildId = 2L;
        CharacterResponse response = new CharacterResponse(characterId, 1L, "Heroe", null, WarriorClass.GUERRERO);

        // Path name requires 'builId' matching the implementation's parameter handling, we just substitute path vars directly.
        Mockito.when(updateCharacterBuildUseCase.updateCharacterBuild(eq(characterId), eq(buildId))).thenReturn(response);

        mockMvc.perform(put("/api/characters/{characterId}/build/{buildId}", characterId, buildId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()));
    }

    @Test
    void deleteCharacter_ExistingId_ReturnsNoContent() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/characters/delete/{id}", id))
                .andExpect(status().isNoContent());

        Mockito.verify(deleteCharacterUseCase).deleteCharacter(id);
    }
}
