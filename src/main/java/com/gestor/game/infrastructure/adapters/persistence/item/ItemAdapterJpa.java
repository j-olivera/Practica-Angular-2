package com.gestor.game.infrastructure.adapters.persistence.item;

import com.gestor.game.application.port.out.item.ItemRepositoryPort;
import com.gestor.game.core.entities.item.Item;
import com.gestor.game.core.enums.item.Category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemAdapterJpa implements ItemRepositoryPort{
    private final ItemRepositoryJpa itemRepositoryJpa;
    private final ItemMapperJpa itemMapperJpa;

    public ItemAdapterJpa(ItemRepositoryJpa itemRepositoryJpa, ItemMapperJpa itemMapperJpa) {
        this.itemRepositoryJpa = itemRepositoryJpa;
        this.itemMapperJpa = itemMapperJpa;
    }

    @Override
    public Item save(Item item) {
        ItemEntity itemEntity = itemMapperJpa.toJpaEntity(item);
        itemEntity = itemRepositoryJpa.save(itemEntity);
        return itemMapperJpa.toCoreEntity(itemEntity);
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemRepositoryJpa.findById(id).map(itemMapperJpa::toCoreEntity);
    }

    @Override
    public List<Item> findAll() {
        return itemRepositoryJpa.findAll()
                .stream()
                .map(itemMapperJpa::toCoreEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> findByCategory(Category category) {
        return itemRepositoryJpa.findByCategory(category)
                .stream()
                .map(itemMapperJpa::toCoreEntity)
                .toList(); //mas corto
    }

    @Override
    public void delete(Long id) {
        itemRepositoryJpa.findById(id).ifPresent(itemRepositoryJpa::delete);
    }
}
