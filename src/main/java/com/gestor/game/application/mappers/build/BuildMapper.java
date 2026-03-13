package com.gestor.game.application.mappers.build;

import com.gestor.game.application.dto.build.BuildRequest;
import com.gestor.game.application.dto.build.BuildResponse;
import com.gestor.game.application.mappers.item.ItemMapper;
import com.gestor.game.core.entities.build.Build;
import com.gestor.game.core.entities.item.Item;

import java.util.List;

public class BuildMapper {
    private final ItemMapper itemMapper;

    // Inyectamos el ItemMapper a través del constructor
    public BuildMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    // El Caso de Uso buscará estos items y se los pasará a este metodo
    public Build toEntity(BuildRequest request, Item sword, Item armor, Item mount, Item blessing) {
        return Build.create(request.name(), sword, armor, mount, blessing);
    }

    public BuildResponse toResponse(Build build) {
        return new BuildResponse(
                build.getId(),
                build.getName(),
                itemMapper.toResponse(build.getSword()),
                itemMapper.toResponse(build.getArmor()),
                itemMapper.toResponse(build.getMount()),
                itemMapper.toResponse(build.getBlessing())
        );
    }

    public List<BuildResponse> toResponse(List<Build> builds) {
        return builds.stream().map(this::toResponse).toList();
    }
}
