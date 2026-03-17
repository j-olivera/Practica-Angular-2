package com.gestor.game.infrastructure.adapters.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryJpa extends JpaRepository<UserEntity,Long> {
    boolean existByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
}
