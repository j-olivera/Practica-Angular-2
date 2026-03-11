package com.gestor.game.application.port.out.item;

import com.gestor.game.core.entities.item.Item;
import com.gestor.game.core.enums.item.Category;

import java.util.Optional;

public interface ItemRepositoryPort {
    Item save(Item item);
    Optional<Item> findById(Long id);
    Optional<Item> findByCategory(Category category);
    void delete(Long id);
}
