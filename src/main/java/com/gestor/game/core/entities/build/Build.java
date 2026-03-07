package com.gestor.game.core.entities.build;

import com.gestor.game.core.entities.item.Item;

public class Build {
    private Long id;
    private String name;
    private Item sword;
    private Item armor;
    private Item amount;
    private Item blessing;

    public Build(Long id, String name, Item sword, Item armor, Item amount, Item blessing) {
        this.id = id;
        this.name = name;
        this.sword = sword;
        this.armor = armor;
        this.amount = amount;
        this.blessing = blessing;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Item getSword() {
        return sword;
    }

    public Item getArmor() {
        return armor;
    }

    public Item getAmount() {
        return amount;
    }

    public Item getBlessing() {
        return blessing;
    }
}
