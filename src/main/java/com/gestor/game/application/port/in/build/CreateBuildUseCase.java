package com.gestor.game.application.port.in.build;

import com.gestor.game.application.dto.build.BuildRequest;
import com.gestor.game.application.dto.build.BuildResponse;

public interface CreateBuildUseCase {
    BuildResponse createBuild(BuildRequest buildRequest);
}
