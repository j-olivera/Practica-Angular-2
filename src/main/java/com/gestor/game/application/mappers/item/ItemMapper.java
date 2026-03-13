package com.gestor.game.application.mappers.item;

import com.gestor.game.application.dto.item.ItemRequest;
import com.gestor.game.application.dto.item.ItemResponse;
import com.gestor.game.core.entities.item.Item;

import java.util.List;

public class ItemMapper {
    public Item toEntity(ItemRequest request) {
        return Item.create(request.name(), request.date(), request.statisticPoint(), request.category());
    }

    public ItemResponse toResponse(Item item) {
        return new ItemResponse(item.getId(), item.getName(), item.getStatistic_point(), item.getCategory());
    }

    public List<ItemResponse> toResponse(List<Item> items) {
        return items.stream().map(this::toResponse).toList();
    }
}
