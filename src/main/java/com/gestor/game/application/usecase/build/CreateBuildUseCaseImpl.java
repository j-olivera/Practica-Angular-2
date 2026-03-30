package com.gestor.game.application.usecase.build;

import com.gestor.game.application.dto.build.BuildRequest;
import com.gestor.game.application.dto.build.BuildResponse;
import com.gestor.game.application.mappers.build.BuildMapper;
import com.gestor.game.application.mappers.item.ItemMapper;
import com.gestor.game.application.port.in.build.CreateBuildUseCase;
import com.gestor.game.application.port.out.build.BuildRepositoryPort;
import com.gestor.game.application.port.out.item.ItemRepositoryPort;
import com.gestor.game.core.entities.build.Build;
import com.gestor.game.core.entities.item.Item;
import com.gestor.game.core.exceptions.NameNotValidException;
import com.gestor.game.core.exceptions.item.ItemNotFoundException;

public class CreateBuildUseCaseImpl implements CreateBuildUseCase {
    private final BuildRepositoryPort buildRepositoryPort;
    private final ItemRepositoryPort itemRepositoryPort;
    private final BuildMapper buildMapper;
    private final ItemMapper itemMapper;

    public CreateBuildUseCaseImpl(BuildRepositoryPort buildRepositoryPort, ItemRepositoryPort itemRepositoryPort, BuildMapper buildMapper, ItemMapper itemMapper) {
        this.buildRepositoryPort = buildRepositoryPort;
        this.itemRepositoryPort = itemRepositoryPort;
        this.buildMapper = buildMapper;
        this.itemMapper = itemMapper;
    }

    @Override
    public BuildResponse createBuild(BuildRequest buildRequest) {
        //primero hay que verificar que los items existen
        Item item1 = itemRepositoryPort.findById(buildRequest.swordId())
                .orElseThrow(()-> new ItemNotFoundException("Item not found"));
        Item item2 = itemRepositoryPort.findById(buildRequest.armorId())
                .orElseThrow(()-> new ItemNotFoundException("Item not found"));
        Item item3 = itemRepositoryPort.findById(buildRequest.mountId())
                .orElseThrow(()-> new ItemNotFoundException("Item not found"));
        Item item4 = itemRepositoryPort.findById(buildRequest.blessingId())
                .orElseThrow(()-> new ItemNotFoundException("Item not found"));
        //no se permiten nombres repetidos en la builds
        Build build = buildRepositoryPort.findByName(buildRequest.name())
                .orElseThrow(()-> new NameNotValidException("Name is already in use"));

        Build newBuild = buildMapper.toEntity(buildRequest, item1,item2,item3,item4);
        Build saved = buildRepositoryPort.save(newBuild);
        return buildMapper.toResponse(saved);
    }
}

