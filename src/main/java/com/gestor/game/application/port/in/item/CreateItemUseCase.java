package com.gestor.game.application.port.in.item;

import com.gestor.game.application.dto.item.ItemRequest;
import com.gestor.game.application.dto.item.ItemResponse;

public interface CreateItemUseCase {
    ItemResponse createItem(ItemRequest itemRequest);
}
