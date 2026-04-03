package com.gestor.game.application.mappers.user;

import com.gestor.game.application.dto.user.UserResponse;
import com.gestor.game.core.entities.user.User;

import java.util.List;

public class UserMapper {
    public User toNewUser(String name, String email, String password) {
        return User.create(name, email, password);
    }
    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
    public List<UserResponse> toResponse(List<User> users) {
        return users.stream()
                .map(this::toResponse)
                .toList();
    }
}
