package com.gestor.game.infrastructure.adapters.persistence.build;

import com.gestor.game.core.entities.build.Build;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuildRepositoryJpa extends JpaRepository<BuildEntity, Long> {
    Optional<BuildEntity> findByName(String name);
}
//PONER SIEMPRE SU ENTIDAD DE JPA Y NO DEL CORO DIO MIO QUE PROBLEMAS