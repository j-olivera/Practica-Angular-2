package com.gestor.game.application.port.in.build;

import com.gestor.game.application.dto.build.BuildResponse;

import java.util.List;

public interface RetrieveBuildUseCase {
    BuildResponse getBuildById(Long id);
    List<BuildResponse> getAllBuilds();
    BuildResponse getBuildByName(String name);
}
