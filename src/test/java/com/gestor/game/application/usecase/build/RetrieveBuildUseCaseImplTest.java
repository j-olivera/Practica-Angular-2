package com.gestor.game.application.usecase.build;

import com.gestor.game.application.dto.build.BuildResponse;
import com.gestor.game.application.mappers.build.BuildMapper;
import com.gestor.game.application.port.out.build.BuildRepositoryPort;
import com.gestor.game.core.entities.build.Build;
import com.gestor.game.core.exceptions.build.BuildDontExistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrieveBuildUseCaseImplTest {

    @Mock
    private BuildRepositoryPort buildRepositoryPort;

    @Mock
    private BuildMapper buildMapper;

    @InjectMocks
    private RetrieveBuildUseCaseImpl retrieveBuildUseCase;

    @Test
    void getBuildById_ExistingId_ReturnsBuild() {
        // Arrange
        Long buildId = 1L;
        Build buildEntity = mock(Build.class);
        BuildResponse expectedResponse = mock(BuildResponse.class);

        when(buildRepositoryPort.findById(buildId)).thenReturn(Optional.of(buildEntity));
        when(buildMapper.toResponse(buildEntity)).thenReturn(expectedResponse);

        // Act
        BuildResponse response = retrieveBuildUseCase.getBuildById(buildId);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    void getBuildById_NonExistingId_ThrowsException() {
        // Arrange
        Long buildId = 99L;
        when(buildRepositoryPort.findById(buildId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BuildDontExistException.class, () -> retrieveBuildUseCase.getBuildById(buildId));
    }

    @Test
    void getAllBuilds_ExistingBuilds_ReturnsList() {
        // Arrange
        List<Build> builds = List.of(mock(Build.class));
        List<BuildResponse> expectedResponse = List.of(mock(BuildResponse.class));

        when(buildRepositoryPort.getAllBuilds()).thenReturn(builds);
        when(buildMapper.toResponse(builds)).thenReturn(expectedResponse);

        // Act
        List<BuildResponse> response = retrieveBuildUseCase.getAllBuilds();

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    void getAllBuilds_NoBuilds_ThrowsException() {
        // Arrange
        when(buildRepositoryPort.getAllBuilds()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(BuildDontExistException.class, () -> retrieveBuildUseCase.getAllBuilds());
    }

    @Test
    void getBuildByName_ExistingName_ReturnsBuild() {
        // Arrange
        String name = "Test";
        Build buildEntity = mock(Build.class);
        BuildResponse expectedResponse = mock(BuildResponse.class);

        when(buildRepositoryPort.findByName(name)).thenReturn(Optional.of(buildEntity));
        when(buildMapper.toResponse(buildEntity)).thenReturn(expectedResponse);

        // Act
        BuildResponse response = retrieveBuildUseCase.getBuildByName(name);

        // Assert
        assertEquals(expectedResponse, response);
    }
}
