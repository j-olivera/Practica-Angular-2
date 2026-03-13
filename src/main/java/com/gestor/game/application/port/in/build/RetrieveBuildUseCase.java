package com.gestor.game.application.port.in.build;

import com.gestor.game.application.dto.build.BuildResponse;
import com.gestor.game.core.entities.build.Build;

import java.util.List;

public interface RetrieveBuildUseCase {
    BuildResponse getBuildById(Long id);
    List<BuildResponse> getAllBuilds();
    BuildResponse getBuildByName(String name);
}
