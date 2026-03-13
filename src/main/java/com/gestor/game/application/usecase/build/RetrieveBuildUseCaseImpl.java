package com.gestor.game.application.usecase.build;

import com.gestor.game.application.dto.build.BuildResponse;
import com.gestor.game.application.mappers.build.BuildMapper;
import com.gestor.game.application.port.in.build.RetrieveBuildUseCase;
import com.gestor.game.application.port.out.build.BuildRepositoryPort;
import com.gestor.game.core.entities.build.Build;
import com.gestor.game.core.exceptions.build.BuildDontExistException;

import java.util.List;

public class RetrieveBuildUseCaseImpl implements RetrieveBuildUseCase {
    private final BuildRepositoryPort buildRepositoryPort;
    private final BuildMapper buildMapper;

    public RetrieveBuildUseCaseImpl(BuildRepositoryPort buildRepositoryPort, BuildMapper buildMapper) {
        this.buildRepositoryPort = buildRepositoryPort;
        this.buildMapper = buildMapper;
    }

    @Override
    public BuildResponse getBuildById(Long id) {

        Build build = buildRepositoryPort.findById(id)
                .orElseThrow(()-> new BuildDontExistException("Build Not Found"));

        return buildMapper.toResponse(build);
    }

    @Override
    public List<BuildResponse> getAllBuilds() {
        List<Build> builds = buildRepositoryPort.getAllBuilds();
        if (builds.isEmpty()) {
            throw new BuildDontExistException("No builds found");
        }
        return buildMapper.toResponse(builds);
    }

    @Override
    public BuildResponse getBuildByName(String name) {
        Build build = buildRepositoryPort.findByName(name)
                .orElseThrow(()-> new BuildDontExistException("Build with name " + name + " not found"));
        return buildMapper.toResponse(build);
    }
}
