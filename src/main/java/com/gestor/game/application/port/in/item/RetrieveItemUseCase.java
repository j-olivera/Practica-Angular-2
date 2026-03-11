package com.gestor.game.application.port.in.item;

import com.gestor.game.application.dto.item.ItemResponse;
import com.gestor.game.core.enums.item.Category;

import java.util.List;

public interface RetrieveItemUseCase {
    ItemResponse getItemById(Long id);
    List<ItemResponse> getAllItems();
    List<ItemResponse> getItemsByCategory(Category category);
}
