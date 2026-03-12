package com.gestor.game.application.port.out.user;

import com.gestor.game.core.entities.user.User;

import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    void delete(Long id);
    boolean existsByEmail(String email);
}
