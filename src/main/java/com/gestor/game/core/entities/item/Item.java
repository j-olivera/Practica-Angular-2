package com.gestor.game.core.entities.item;

import com.gestor.game.core.enums.item.Category;

public class Item {

    private Long id;
    private String name;
    private int statistic_point;
    private Category category;

    public Item(Long id, String name, int statistic_point, Category category) {
        this.id = id;
        this.name = name;
        this.statistic_point = statistic_point;
        this.category = category;
    }

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
