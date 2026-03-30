package com.gestor.game.application.usecase.build;

import com.gestor.game.application.dto.build.BuildRequest;
import com.gestor.game.application.dto.build.BuildResponse;
import com.gestor.game.application.mappers.build.BuildMapper;
import com.gestor.game.application.port.out.build.BuildRepositoryPort;
import com.gestor.game.application.port.out.item.ItemRepositoryPort;
import com.gestor.game.core.entities.build.Build;
import com.gestor.game.core.entities.item.Item;
import com.gestor.game.core.enums.item.Category;
import com.gestor.game.core.exceptions.NameNotValidException;
import com.gestor.game.core.exceptions.item.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateBuildUseCaseImplTest {

    @Mock
    private BuildRepositoryPort buildRepositoryPort;

    @Mock
    private ItemRepositoryPort itemRepositoryPort;

    @Mock
    private BuildMapper buildMapper;

    @InjectMocks
    private CreateBuildUseCaseImpl createBuildUseCase;

    @Test
    void createBuild_ValidData_ReturnsBuildResponse() {
        // Arrange
        BuildRequest request = new BuildRequest("Build Suprema", 1L, 2L, 3L, 4L);
        Item sword = Item.reconstruct(1L, "S", 10, Category.ESPADA);
        Item armor = Item.reconstruct(2L, "A", 10, Category.ARMADURA);
        Item mount = Item.reconstruct(3L, "M", 10, Category.MONTURA);
        Item blessing = Item.reconstruct(4L, "B", 10, Category.BENDICION);
        
        Build buildEntity = Build.create("Build Suprema", sword, armor, mount, blessing);
        BuildResponse expectedResponse = new BuildResponse(1L, "Build Suprema", null, null, null, null); // assuming nested objects in response

        when(itemRepositoryPort.findById(1L)).thenReturn(Optional.of(sword));
        when(itemRepositoryPort.findById(2L)).thenReturn(Optional.of(armor));
        when(itemRepositoryPort.findById(3L)).thenReturn(Optional.of(mount));
        when(itemRepositoryPort.findById(4L)).thenReturn(Optional.of(blessing));

        when(buildRepositoryPort.findByName("Build Suprema")).thenReturn(Optional.empty());
        when(buildMapper.toEntity(request, sword, armor, mount, blessing)).thenReturn(buildEntity);
        when(buildRepositoryPort.save(buildEntity)).thenReturn(buildEntity);
        when(buildMapper.toResponse(buildEntity)).thenReturn(expectedResponse);

        // Act
        BuildResponse response = createBuildUseCase.createBuild(request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse.name(), response.name());
        verify(buildRepositoryPort).save(buildEntity);
    }

    @Test
    void createBuild_ItemNotFound_ThrowsException() {
        // Arrange
        BuildRequest request = new BuildRequest("Build", 1L, 2L, 3L, 4L);
        when(itemRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ItemNotFoundException.class, () -> createBuildUseCase.createBuild(request));
    }

    @Test
    void createBuild_NameAlreadyExists_ThrowsException() {
        // Arrange
        BuildRequest request = new BuildRequest("Build Duplicada", 1L, 2L, 3L, 4L);
        Item sword = Item.reconstruct(1L, "S", 10, Category.ESPADA);
        
        when(itemRepositoryPort.findById(anyLong())).thenReturn(Optional.of(sword));
        when(buildRepositoryPort.findByName("Build Duplicada")).thenReturn(Optional.of(mock(Build.class)));

        // Act & Assert
        assertThrows(NameNotValidException.class, () -> createBuildUseCase.createBuild(request));
        verify(buildRepositoryPort, never()).save(any());
    }
}
