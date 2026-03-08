package com.gestor.game.core.entities.build;

import com.gestor.game.core.entities.item.Item;
import com.gestor.game.core.exceptions.NullException;
import com.gestor.game.core.exceptions.NameNotValidException;

public class Build {
    private Long id;
    private String name;
    private Item sword;
    private Item armor;
    private Item mount;
    private Item blessing;

    private Build(Long id, String name, Item sword, Item armor, Item mount, Item blessing) {
        this.id = id;
        this.name = name;
        this.sword = sword;
        this.armor = armor;
        this.mount = mount;
        this.blessing = blessing;
    }

    public static Build create(String name, Item sword, Item armor, Item amount, Item blessing) {
        validate(sword, armor, amount, blessing, name);
        return new Build(null, name, sword, armor, amount, blessing);
    }

    public static Build reconstruct(Long id, String name, Item sword, Item armor, Item amount, Item blessing) {
        return new Build(id, name, sword, armor, amount, blessing);
    }

    public static void validate(Item sword, Item armor, Item amount, Item blessing, String name) {
        if (sword == null || armor == null || amount == null || blessing == null || name == null) {
            throw new NullException("No se permiten valores nulos");
        }
        if(name.length() < 3 || name.trim().isEmpty() || name.length() > 20 ) {
            throw new NameNotValidException("Nombre no valido");
        }
    }

    //hay que seguir con las siguientes entidades replicando este mismo patron SIEMPRE

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

    public Item getMount() {
        return mount;
    }

    public Item getBlessing() {
        return blessing;
    }
}
