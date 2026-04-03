package com.gestor.game.infrastructure.adapters.web.build;

import com.gestor.game.application.dto.build.BuildRequest;
import com.gestor.game.application.dto.build.BuildResponse;
import com.gestor.game.application.port.in.build.CreateBuildUseCase;
import com.gestor.game.application.port.in.build.RetrieveBuildUseCase;
import com.gestor.game.core.entities.build.Build;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/builds")
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirement(name = "bearerAuth")
public class BuildController {
    private final CreateBuildUseCase createBuildUseCase;
    private final RetrieveBuildUseCase retrieveBuildUseCase;

    public BuildController(CreateBuildUseCase createBuildUseCase, RetrieveBuildUseCase retrieveBuildUseCase) {
        this.createBuildUseCase = createBuildUseCase;
        this.retrieveBuildUseCase = retrieveBuildUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<BuildResponse> createBuild(@RequestBody BuildRequest buildRequest){
        BuildResponse response = createBuildUseCase.createBuild(buildRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildResponse> getBuildById(@PathVariable Long id){
        BuildResponse response = retrieveBuildUseCase.getBuildById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<BuildResponse> getBuildByName(@PathVariable String name){
        BuildResponse response = retrieveBuildUseCase.getBuildByName(name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<BuildResponse>> getAllBuilds(){
        List<BuildResponse> builds = retrieveBuildUseCase.getAllBuilds();
        return new ResponseEntity<>(builds, HttpStatus.OK);
    }

}
