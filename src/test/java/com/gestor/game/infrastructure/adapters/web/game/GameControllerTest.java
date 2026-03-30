package com.gestor.game.infrastructure.adapters.web.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestor.game.application.dto.game.GameRequest;
import com.gestor.game.application.dto.game.GameResponse;
import com.gestor.game.application.port.in.game.RegisterGameUseCase;
import com.gestor.game.application.port.in.game.RetrieveGameHistoryUseCase;
import com.gestor.game.core.enums.game.Result;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegisterGameUseCase registerGameUseCase;

    @MockBean
    private RetrieveGameHistoryUseCase retrieveGameHistoryUseCase;

    @Test
    void createGame_ValidRequest_ReturnsCreated() throws Exception {
        GameRequest request = new GameRequest(1L, Result.COMPLETED);
        GameResponse response = new GameResponse(1L, 1L, LocalDate.now(), Result.COMPLETED);

        Mockito.when(registerGameUseCase.registerGame(any(GameRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/games/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.userId").value(response.userId()));
    }

    @Test
    void getGameById_ExistingId_ReturnsOk() throws Exception {
        Long id = 1L;
        GameResponse response = new GameResponse(1L, 1L, LocalDate.now(), Result.COMPLETED);

        Mockito.when(retrieveGameHistoryUseCase.getGameById(id)).thenReturn(response);

        mockMvc.perform(get("/api/games/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()));
    }

    @Test
    void getGameUserById_ExistingUser_ReturnsListOk() throws Exception {
        Long userId = 1L;
        GameResponse response = new GameResponse(1L, userId, LocalDate.now(), Result.COMPLETED);

        Mockito.when(retrieveGameHistoryUseCase.getGameByUserId(userId)).thenReturn(List.of(response));

        mockMvc.perform(get("/api/games/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(response.id()));
    }
}
