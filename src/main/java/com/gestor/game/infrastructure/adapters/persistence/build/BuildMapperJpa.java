package com.gestor.game.infrastructure.adapters.persistence.build;

import com.gestor.game.core.entities.build.Build;
import com.gestor.game.infrastructure.adapters.persistence.item.ItemMapperJpa;

public class BuildMapperJpa {
    private final ItemMapperJpa itemMapper;

    public BuildMapperJpa(ItemMapperJpa itemMapper) {
        this.itemMapper = itemMapper;
    }

    public BuildEntity toJpaEntity(Build build) {
        if (build == null) return null;
        //usar el mapper en los parametros, debe ser si o si una entidad jpa lo que se use
        return null;
    }

    public Build toCoreEntity(BuildEntity entity) {
        if (entity == null) return null;
        return null;
    }
}
