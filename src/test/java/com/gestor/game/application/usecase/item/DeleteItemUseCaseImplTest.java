package com.gestor.game.application.usecase.item;

import com.gestor.game.application.port.out.item.ItemRepositoryPort;
import com.gestor.game.core.entities.item.Item;
import com.gestor.game.core.enums.item.Category;
import com.gestor.game.core.exceptions.item.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteItemUseCaseImplTest {

    @Mock
    private ItemRepositoryPort itemRepositoryPort;

    @InjectMocks
    private DeleteItemUseCaseImpl deleteItemUseCase;

    @Test
    void deleteItem_ExistingId_DeletesItem() {
        // Arrange
        Long itemId = 1L;
        Item itemEntity = Item.reconstruct(itemId, "Escudo", 5, Category.ARMADURA);
        when(itemRepositoryPort.findById(itemId)).thenReturn(Optional.of(itemEntity));

        // Act
        deleteItemUseCase.deleteItem(itemId);

        // Assert
        verify(itemRepositoryPort).findById(itemId);
        verify(itemRepositoryPort).delete(itemId);
    }

    @Test
    void deleteItem_NonExistingId_ThrowsException() {
        // Arrange
        Long itemId = 99L;
        when(itemRepositoryPort.findById(itemId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ItemNotFoundException.class, () -> deleteItemUseCase.deleteItem(itemId));
        
        verify(itemRepositoryPort, never()).delete(anyLong());
    }
}
