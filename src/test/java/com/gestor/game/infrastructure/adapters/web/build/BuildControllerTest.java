package com.gestor.game.infrastructure.adapters.web.build;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestor.game.application.dto.build.BuildRequest;
import com.gestor.game.application.dto.build.BuildResponse;
import com.gestor.game.application.port.in.build.CreateBuildUseCase;
import com.gestor.game.application.port.in.build.RetrieveBuildUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BuildController.class)
class BuildControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateBuildUseCase createBuildUseCase;

    @MockBean
    private RetrieveBuildUseCase retrieveBuildUseCase;

    @Test
    void createBuild_ValidRequest_ReturnsCreated() throws Exception {
        BuildRequest request = new BuildRequest("Build", 1L, 2L, 3L, 4L);
        BuildResponse response = new BuildResponse(1L, "Build", null, null, null, null);

        Mockito.when(createBuildUseCase.createBuild(any(BuildRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/builds/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.name").value(response.name()));
    }

    @Test
    void getBuildById_ExistingId_ReturnsOk() throws Exception {
        Long id = 1L;
        BuildResponse response = new BuildResponse(id, "Build", null, null, null, null);

        Mockito.when(retrieveBuildUseCase.getBuildById(id)).thenReturn(response);

        mockMvc.perform(get("/api/builds/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()));
    }

    @Test
    void getBuildByName_ExistingName_ReturnsOk() throws Exception {
        String name = "Build";
        BuildResponse response = new BuildResponse(1L, name, null, null, null, null);

        Mockito.when(retrieveBuildUseCase.getBuildByName(name)).thenReturn(response);

        mockMvc.perform(get("/api/builds/name/{name}", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(response.name()));
    }

    @Test
    void getAllBuilds_ReturnsListOk() throws Exception {
        BuildResponse response = new BuildResponse(1L, "Build", null, null, null, null);

        Mockito.when(retrieveBuildUseCase.getAllBuilds()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/builds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(response.id()));
    }
}
