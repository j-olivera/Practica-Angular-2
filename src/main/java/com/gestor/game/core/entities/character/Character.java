package com.gestor.game.core.entities.character;

import com.gestor.game.core.entities.build.Build;
import com.gestor.game.core.entities.user.User;
import com.gestor.game.core.enums.character.WarriorClass;

public class Character {

    private Long id;
    private User user;
    private String name;
    private Build build;
    private WarriorClass warriorClass;

    public Character(Long id, User user, String name, Build build, WarriorClass warriorClass) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.build = build;
        this.warriorClass = warriorClass;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public Build getBuild() {
        return build;
    }

    public WarriorClass getWarriorClass() {
        return warriorClass;
    }
}
