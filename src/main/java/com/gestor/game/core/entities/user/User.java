package com.gestor.game.core.entities.user;

import com.gestor.game.core.exceptions.NullException;

public class User {
    private Long id;
    private String name;
    private String email;
    private String password;

    private User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static User create( String name, String email, String password) {
        validate(name, email, password);
        return new User(null, name, email, password);
    }

    public static User reconstruct(Long id, String name, String email, String password) {
        return new User(id, name, email, password);
    }

    public static void validate(String name, String email, String password) {
        if(name == null || name.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new NullException("No se permiten valores vacíos ni nulos");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
