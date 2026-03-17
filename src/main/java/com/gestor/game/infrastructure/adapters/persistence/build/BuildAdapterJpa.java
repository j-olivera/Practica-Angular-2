package com.gestor.game.infrastructure.adapters.persistence.build;

import com.gestor.game.application.port.out.build.BuildRepositoryPort;
import com.gestor.game.core.entities.build.Build;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BuildAdapterJpa implements BuildRepositoryPort {
    private final BuildRepositoryJpa buildRepositoryJpa;
    private final BuildMapperJpa buildMapperJpa;

    public BuildAdapterJpa(BuildRepositoryJpa buildRepositoryJpa, BuildMapperJpa buildMapperJpa) {
        this.buildRepositoryJpa = buildRepositoryJpa;
        this.buildMapperJpa = buildMapperJpa;
    }

    @Override
    public Build save(Build build) {
        BuildEntity entity = buildMapperJpa.toJpaEntity(build);
        BuildEntity savedEntity = buildRepositoryJpa.save(entity);
        return buildMapperJpa.toCoreEntity(savedEntity);
    }

    @Override
    public Optional<Build> findById(Long id) {
        return buildRepositoryJpa.findById(id).map(buildMapperJpa::toCoreEntity);
    }

    @Override
    public Optional<Build> findByName(String name) {
        return buildRepositoryJpa.findByName(name);
    }

    @Override
    public List<Build> getAllBuilds() {
        return buildRepositoryJpa.findAll()
                .stream()
                .map(buildMapperJpa::toCoreEntity)
                .toList();
    }

    @Override
    public void delete(Long id) {
        buildRepositoryJpa.findById(id).ifPresent(buildRepositoryJpa::delete);
    }
}
