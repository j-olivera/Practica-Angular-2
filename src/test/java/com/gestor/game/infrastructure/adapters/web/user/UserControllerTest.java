package com.gestor.game.infrastructure.adapters.web.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestor.game.application.dto.user.UserRequest;
import com.gestor.game.application.dto.user.UserResponse;
import com.gestor.game.application.port.in.user.CreateUserUseCase;
import com.gestor.game.application.port.in.user.RetrieveUserUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateUserUseCase createUserUseCase;

    @MockBean
    private RetrieveUserUseCase retrieveUserUseCase;

    @Test
    void createUser_ValidRequest_ReturnsCreated() throws Exception {
        UserRequest request = new UserRequest("Juan", "test@test.com", "pass");
        UserResponse response = new UserResponse(1L, "Juan", "test@test.com");

        Mockito.when(createUserUseCase.createUser(any(UserRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.name").value(response.name()))
                .andExpect(jsonPath("$.email").value(response.email()));
    }

    @Test
    void retrieveUser_ExistingId_ReturnsOk() throws Exception {
        Long id = 1L;
        UserResponse response = new UserResponse(id, "Juan", "test@test.com");

        Mockito.when(retrieveUserUseCase.getUserById(id)).thenReturn(response);

        mockMvc.perform(get("/api/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.name").value(response.name()));
    }
}
