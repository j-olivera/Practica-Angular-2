package com.gestor.game.application.usecase.item;

import com.gestor.game.application.dto.item.ItemResponse;
import com.gestor.game.application.mappers.item.ItemMapper;
import com.gestor.game.application.port.out.item.ItemRepositoryPort;
import com.gestor.game.core.entities.item.Item;
import com.gestor.game.core.enums.item.Category;
import com.gestor.game.core.exceptions.item.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrieveItemUseCaseImplTest {

    @Mock
    private ItemRepositoryPort itemRepositoryPort;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private RetrieveItemUseCaseImpl retrieveItemUseCase;

    @Test
    void getItemById_ExistingId_ReturnsItem() {
        // Arrange
        Long itemId = 1L;
        Item itemEntity = Item.reconstruct(itemId, "Item", 10, Category.ESPADA);
        ItemResponse expectedResponse = new ItemResponse(itemId, "Item", 10, Category.ESPADA);

        when(itemRepositoryPort.findById(itemId)).thenReturn(Optional.of(itemEntity));
        when(itemMapper.toResponse(itemEntity)).thenReturn(expectedResponse);

        // Act
        ItemResponse response = retrieveItemUseCase.getItemById(itemId);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse.id(), response.id());
    }

    @Test
    void getItemById_NonExistingId_ThrowsException() {
        // Arrange
        Long itemId = 99L;
        when(itemRepositoryPort.findById(itemId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ItemNotFoundException.class, () -> retrieveItemUseCase.getItemById(itemId));
    }

    @Test
    void getAllItems_ExistingItems_ReturnsList() {
        // Arrange
        Item itemEntity = Item.reconstruct(1L, "Item", 10, Category.ESPADA);
        List<Item> items = List.of(itemEntity);
        List<ItemResponse> expectedResponse = List.of(new ItemResponse(1L, "Item", 10, Category.ESPADA));

        when(itemRepositoryPort.findAll()).thenReturn(items);
        when(itemMapper.toResponse(items)).thenReturn(expectedResponse);

        // Act
        List<ItemResponse> response = retrieveItemUseCase.getAllItems();

        // Assert
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
    }

    @Test
    void getAllItems_NoItems_ThrowsException() {
        // Arrange
        when(itemRepositoryPort.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(ItemNotFoundException.class, () -> retrieveItemUseCase.getAllItems());
    }

    @Test
    void getItemsByCategory_ExistingItems_ReturnsList() {
        // Arrange
        Category category = Category.ESPADA;
        Item itemEntity = Item.reconstruct(1L, "Item", 10, category);
        List<Item> items = List.of(itemEntity);
        List<ItemResponse> expectedResponse = List.of(new ItemResponse(1L, "Item", 10, category));

        when(itemRepositoryPort.findByCategory(category)).thenReturn(items);
        when(itemMapper.toResponse(items)).thenReturn(expectedResponse);

        // Act
        List<ItemResponse> response = retrieveItemUseCase.getItemsByCategory(category);

        // Assert
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
    }

    @Test
    void getItemsByCategory_NoItems_ThrowsException() {
        // Arrange
        Category category = Category.ESPADA;
        when(itemRepositoryPort.findByCategory(category)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(ItemNotFoundException.class, () -> retrieveItemUseCase.getItemsByCategory(category));
    }
}
