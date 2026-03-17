package com.gestor.game.infrastructure.adapters.persistence.item;

import com.gestor.game.core.enums.item.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemRepositoryJpa extends JpaRepository<ItemEntity,Long> {
    List<ItemEntity> findByCategory(Category category);
}
