package com.gestor.game.infrastructure.adapters.persistence.build;

import com.gestor.game.core.entities.build.Build;
import com.gestor.game.infrastructure.adapters.persistence.item.ItemMapperJpa;
import org.springframework.stereotype.Component;

@Component
public class BuildMapperJpa {
    private final ItemMapperJpa itemMapper;

    public BuildMapperJpa(ItemMapperJpa itemMapper) {
        this.itemMapper = itemMapper;
    }

    public BuildEntity toJpaEntity(Build build) {
        if (build == null) return null;
        //usar el mapper en los parametros, debe ser si o si una entidad jpa lo que se use
        return new BuildEntity(
                build.getId(),
                build.getName(),
                itemMapper.toJpaEntity(build.getSword()),
                itemMapper.toJpaEntity(build.getArmor()),
                itemMapper.toJpaEntity(build.getMount()),
                itemMapper.toJpaEntity(build.getBlessing())
        );
    }

    public Build toCoreEntity(BuildEntity entity) {
        if (entity == null) return null;
        return Build.reconstruct(
                entity.getId(),
                entity.getName(),
                itemMapper.toCoreEntity(entity.getSword()),
                itemMapper.toCoreEntity(entity.getArmor()),
                itemMapper.toCoreEntity(entity.getMount()),
                itemMapper.toCoreEntity(entity.getBlessing())
        );
    }
}
