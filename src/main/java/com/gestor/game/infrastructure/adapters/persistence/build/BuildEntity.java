package com.gestor.game.infrastructure.adapters.persistence.build;

import com.gestor.game.core.entities.item.Item;
import com.gestor.game.infrastructure.adapters.persistence.item.ItemEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "builds")
public class BuildEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sword_id", nullable = false)
    private ItemEntity sword;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "armor_id", nullable = false)
    private ItemEntity armor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mount_id", nullable = false)
    private ItemEntity mount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blessing_id", nullable = false)
    private ItemEntity blessing;

    public BuildEntity(Long id, String name, ItemEntity sword, ItemEntity armor, ItemEntity mount, ItemEntity blessing) {
        this.id = id;
        this.name = name;
        this.sword = sword;
        this.armor = armor;
        this.mount = mount;
        this.blessing = blessing;
    }

    public BuildEntity() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ItemEntity getSword() {
        return sword;
    }

    public ItemEntity getArmor() {
        return armor;
    }

    public ItemEntity getMount() {
        return mount;
    }

    public ItemEntity getBlessing() {
        return blessing;
    }
}
/*
Una Build está compuesta por 4 ítems.
Dado que un ítem (por ejemplo, la espada "Excalibur")
existe una sola vez en el inventario global del juego,
pero puede ser equipada en miles de Builds diferentes,
 la relación correcta es de Muchos a Uno (@ManyToOne).
 */