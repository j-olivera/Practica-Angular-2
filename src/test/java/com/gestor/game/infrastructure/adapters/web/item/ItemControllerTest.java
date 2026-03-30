package com.gestor.game.infrastructure.adapters.web.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestor.game.application.dto.item.ItemRequest;
import com.gestor.game.application.dto.item.ItemResponse;
import com.gestor.game.application.port.in.item.CreateItemUseCase;
import com.gestor.game.application.port.in.item.DeleteItemUseCase;
import com.gestor.game.application.port.in.item.RetrieveItemUseCase;
import com.gestor.game.core.enums.item.Category;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateItemUseCase createItemUseCase;

    @MockBean
    private DeleteItemUseCase deleteItemUseCase;

    @MockBean
    private RetrieveItemUseCase retrieveItemUseCase;

    @Test
    void createItem_ValidRequest_ReturnsCreated() throws Exception {
        ItemRequest request = new ItemRequest("Espada de Hierro", LocalDateTime.now(), 15, Category.ESPADA);
        ItemResponse response = new ItemResponse(1L, "Espada de Hierro", 15, Category.ESPADA);

        Mockito.when(createItemUseCase.createItem(any(ItemRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/items/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.name").value(response.name()));
    }

    @Test
    void getItem_ExistingId_ReturnsOk() throws Exception {
        Long id = 1L;
        ItemResponse response = new ItemResponse(id, "Espada de Hierro", 15, Category.ESPADA);

        Mockito.when(retrieveItemUseCase.getItemById(id)).thenReturn(response);

        mockMvc.perform(get("/api/items/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.name").value(response.name()));
    }

    @Test
    void deleteItem_ExistingId_ReturnsNoContent() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/items/delete/{id}", id))
                .andExpect(status().isNoContent());

        Mockito.verify(deleteItemUseCase).deleteItem(id);
    }
}
