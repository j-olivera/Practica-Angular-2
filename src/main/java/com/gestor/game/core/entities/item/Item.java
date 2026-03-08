package com.gestor.game.core.entities.item;

import com.gestor.game.core.enums.item.Category;
import com.gestor.game.core.exceptions.NameNotValidException;
import com.gestor.game.core.exceptions.NullException;

import java.time.LocalDateTime;

public class Item {

    private Long id;
    private String name;
    private int statistic_point;
    private Category category;

    private Item(Long id, String name, int statistic_point, Category category) {
        this.id = id;
        this.name = name;
        this.statistic_point = statistic_point;
        this.category = category;
    }

    public static Item create(String name, LocalDateTime date, int statistic_point, Category category) {
        validate(name, date, statistic_point, category);
        return new Item(null, name, statistic_point, category);
    }

    public static Item reconstruct(Long id, String name, LocalDateTime date, int statistic_point, Category category) {
        return new Item(id, name, statistic_point, category);
    }

    public static void validate(String name, LocalDateTime date, int statistic_point, Category category) {
        if(name==null || date == null || statistic_point < 0 || category == null){
            throw new NullException("Valores nulos o negativos no validos");
        }
        if(name.trim().isEmpty() || name.length() < 3 || name.length() > 50){
            throw new NameNotValidException("Nombre no valido");
        }
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
