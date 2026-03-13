package com.gestor.game.application.usecase.item;

import com.gestor.game.application.dto.item.ItemRequest;
import com.gestor.game.application.dto.item.ItemResponse;
import com.gestor.game.application.mappers.item.ItemMapper;
import com.gestor.game.application.port.in.item.CreateItemUseCase;
import com.gestor.game.application.port.out.item.ItemRepositoryPort;
import com.gestor.game.core.entities.item.Item;

public class CreateItemUseCaseImpl implements CreateItemUseCase {
    private final ItemRepositoryPort itemRepositoryPort;
    private final ItemMapper itemMapper;

    public CreateItemUseCaseImpl(ItemRepositoryPort itemRepositoryPort, ItemMapper itemMapper) {
        this.itemRepositoryPort = itemRepositoryPort;
        this.itemMapper = itemMapper;
    }


    @Override
    public ItemResponse createItem(ItemRequest itemRequest) {
        //puede haber items repetidos asi que no se aplica verificación de duplicados
        Item item = itemMapper.toEntity(itemRequest);
        Item savedItem = itemRepositoryPort.save(item);

        return itemMapper.toResponse(savedItem);
    }
}
