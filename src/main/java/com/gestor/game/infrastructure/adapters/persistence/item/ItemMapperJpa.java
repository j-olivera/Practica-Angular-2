package com.gestor.game.infrastructure.adapters.persistence.item;

import com.gestor.game.core.entities.item.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapperJpa {

    public ItemEntity toJpaEntity(Item item) {
        if (item == null) return null;
        return new ItemEntity(
                item.getId(),
                item.getName(),
                item.getStatistic_point(),
                item.getCategory()
        );
    }

    public Item toCoreEntity(ItemEntity entity) {
        if (entity == null) return null;
        return Item.reconstruct(
                entity.getId(),
                entity.getName(),// Asumiendo que agregas la fecha al entity
                entity.getStatistic_point(),
                entity.getCategory()
        );
    }

}
