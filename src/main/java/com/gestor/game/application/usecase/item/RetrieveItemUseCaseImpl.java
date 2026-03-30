package com.gestor.game.application.usecase.item;

import com.gestor.game.application.dto.item.ItemResponse;
import com.gestor.game.application.mappers.item.ItemMapper;
import com.gestor.game.application.port.in.item.RetrieveItemUseCase;
import com.gestor.game.application.port.out.item.ItemRepositoryPort;
import com.gestor.game.core.entities.item.Item;
import com.gestor.game.core.enums.item.Category;
import com.gestor.game.core.exceptions.item.ItemNotFoundException;

import java.util.List;

public class RetrieveItemUseCaseImpl implements RetrieveItemUseCase {
    private final ItemRepositoryPort itemRepositoryPort;
    private final ItemMapper itemMapper;

    public RetrieveItemUseCaseImpl(ItemRepositoryPort itemRepositoryPort, ItemMapper itemMapper) {
        this.itemRepositoryPort = itemRepositoryPort;
        this.itemMapper = itemMapper;
    }

    @Override
    public ItemResponse getItemById(Long id) {
        Item item = itemRepositoryPort.findById(id).orElseThrow(()-> new ItemNotFoundException("Item not found"));
        return itemMapper.toResponse(item);
    }

    @Override
    public List<ItemResponse> getAllItems() {
        List<Item> items = itemRepositoryPort.findAll();
        if(items.isEmpty()){
            throw new ItemNotFoundException("You don't have any items");
        }
        return itemMapper.toResponse(items);
    }

    @Override
    public List<ItemResponse> getItemsByCategory(Category category) {
        List<Item> items = itemRepositoryPort.findByCategory(category);
        if(items.isEmpty()){
            throw new ItemNotFoundException("You don't have any items from this category");
        }
        return itemMapper.toResponse(items);
    }
}
//esto deberia ir en contratos diferentes