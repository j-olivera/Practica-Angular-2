package com.gestor.game.infrastructure.adapters.persistence.item;

import com.gestor.game.core.enums.item.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepositoryJpa extends JpaRepository<ItemEntity,Long> {
    List<ItemEntity> findByCategory(Category category);
}
