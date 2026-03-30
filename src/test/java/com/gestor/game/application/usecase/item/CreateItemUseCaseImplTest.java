package com.gestor.game.application.usecase.item;

import com.gestor.game.application.dto.item.ItemRequest;
import com.gestor.game.application.dto.item.ItemResponse;
import com.gestor.game.application.mappers.item.ItemMapper;
import com.gestor.game.application.port.out.item.ItemRepositoryPort;
import com.gestor.game.core.entities.item.Item;
import com.gestor.game.core.enums.item.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateItemUseCaseImplTest {

    @Mock
    private ItemRepositoryPort itemRepositoryPort;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private CreateItemUseCaseImpl createItemUseCase;

    @Test
    void createItem_ValidData_ReturnsItemResponse() {
        // Arrange
        ItemRequest request = new ItemRequest("Espada", java.time.LocalDateTime.now(), 10, Category.ESPADA);
        Item itemEntity = Item.create("Espada", 10, Category.ESPADA);
        Item savedItem = Item.reconstruct(1L, "Espada", 10, Category.ESPADA);
        ItemResponse expectedResponse = new ItemResponse(1L, "Espada", 10, Category.ESPADA);

        when(itemMapper.toEntity(request)).thenReturn(itemEntity);
        when(itemRepositoryPort.save(itemEntity)).thenReturn(savedItem);
        when(itemMapper.toResponse(savedItem)).thenReturn(expectedResponse);

        // Act
        ItemResponse response = createItemUseCase.createItem(request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse.id(), response.id());
        assertEquals(expectedResponse.name(), response.name());
        
        verify(itemMapper).toEntity(request);
        verify(itemRepositoryPort).save(itemEntity);
        verify(itemMapper).toResponse(savedItem);
    }
}
