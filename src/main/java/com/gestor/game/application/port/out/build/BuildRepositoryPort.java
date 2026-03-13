package com.gestor.game.application.port.out.build;

import com.gestor.game.core.entities.build.Build;

import java.util.List;
import java.util.Optional;

public interface BuildRepositoryPort {
    Build save(Build build);
    Optional<Build> findById(Long id);
    Optional<Build> findByName(String name);
    List<Build> getAllBuilds();
    void delete(Long id);

}
