package com.gestor.game.core.entities.character;

import com.gestor.game.core.entities.build.Build;
import com.gestor.game.core.entities.user.User;
import com.gestor.game.core.enums.character.WarriorClass;
import com.gestor.game.core.exceptions.NameNotValidException;
import com.gestor.game.core.exceptions.NullException;

public class Character {

    private Long id;
    private User user;
    private String name;
    private Build build;
    private WarriorClass warriorClass;

    private Character(Long id, User user, String name, Build build, WarriorClass warriorClass) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.build = build;
        this.warriorClass = warriorClass;
    }

    public static Character create(User user, String name, Build build, WarriorClass warriorClass) {
        validate(user, name, build, warriorClass);
        return new Character(null, user, name, build, warriorClass);
    }

    public static Character reconstruct(Long id,User user, String name, Build build, WarriorClass warriorClass) {
        return new  Character(id, user, name, build, warriorClass);
    }

    public static void validate(User user, String name, Build build, WarriorClass warriorClass) {
       if(user == null || name == null || build == null || warriorClass == null ){
           throw new NullException("Valores nulos no permitidos");
       }
       if(name.trim().isEmpty() || name.length() < 3 || name.length() > 32){
           throw new NameNotValidException("Nombre no valido");
       }
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
