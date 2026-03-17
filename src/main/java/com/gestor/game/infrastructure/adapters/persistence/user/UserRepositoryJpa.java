package com.gestor.game.infrastructure.adapters.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepositoryJpa extends JpaRepository<UserEntity,Long> {
    boolean existByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
}
