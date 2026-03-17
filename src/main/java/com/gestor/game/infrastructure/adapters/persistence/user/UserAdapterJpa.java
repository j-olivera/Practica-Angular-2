package com.gestor.game.infrastructure.adapters.persistence.user;

import com.gestor.game.application.port.out.user.UserRepositoryPort;
import com.gestor.game.core.entities.user.User;
import com.gestor.game.core.exceptions.user.UserDontExistException;

import java.util.Optional;

public class UserAdapterJpa implements UserRepositoryPort {
    private final UserRepositoryJpa userRepositoryJpa;
    private final UserMapperJpa userMapperJpa;

    public UserAdapterJpa(UserRepositoryJpa userRepositoryJpa, UserMapperJpa userMapperJpa) {
        this.userRepositoryJpa = userRepositoryJpa;
        this.userMapperJpa = userMapperJpa;
    }

    @Override
    public User save(User user) {
        UserEntity entity =  userMapperJpa.toEntity(user);
        UserEntity savedEntity = userRepositoryJpa.save(entity);
        return userMapperJpa.toCore(savedEntity);
    }
    //Línea 2 — .map() de Optional funciona así:
    // si el Optional tiene valor, aplica la función
    // y devuelve el resultado envuelto en otro Optional.
    // Si está vacío, no hace nada y devuelve Optional.empty().
    /*La diferencia con tu código original es que antes mezclabas dos contratos —
     le decías a quien llama "te devuelvo un Optional" pero internamente lanzabas
     una excepción antes de que pudiera manejarlo. Ahora el Optional llega intacto
      al use case y es él quien decide qué hacer: */

    @Override
    public Optional<User> findById(Long id) {
        Optional<UserEntity> entity = userRepositoryJpa.findById(id);
        return entity.map(userMapperJpa::toCore);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> entity = userRepositoryJpa.findByEmail(email);
        return entity.map(userMapperJpa::toCore);
    }

    @Override
    public void delete(Long id) {
        userRepositoryJpa.findById(id).ifPresent(userRepositoryJpa::delete);
    }
    /*
        findById(id) → devuelve Optional<UserEntity>, puede tener valor o estar vacío.
        .ifPresent() → si el Optional tiene valor, ejecuta la función. Si está vacío, no hace nada y no explota.
     */

    @Override
    public boolean existsByEmail(String email) {
        return userRepositoryJpa.findByEmail(email).isPresent();
    }
}
