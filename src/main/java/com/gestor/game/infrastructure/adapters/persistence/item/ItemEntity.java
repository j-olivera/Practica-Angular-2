package com.gestor.game.infrastructure.adapters.persistence.item;

import com.gestor.game.core.enums.item.Category;
import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private int statistic_point;
    @Column
    private Category category;

    public ItemEntity(Long id, String name, int statistic_point, Category category) {
        this.id = id;
        this.name = name;
        this.statistic_point = statistic_point;
        this.category = category;
    }

    public ItemEntity() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStatistic_point() {
        return statistic_point;
    }

    public Category getCategory() {
        return category;
    }
}
