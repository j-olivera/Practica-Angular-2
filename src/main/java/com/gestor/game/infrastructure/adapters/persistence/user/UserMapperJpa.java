package com.gestor.game.infrastructure.adapters.persistence.user;

import com.gestor.game.core.entities.user.User;

public class UserMapperJpa {
    public User toCore(UserEntity userEntity){
        if(userEntity==null) return null;
        return User.reconstruct(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getPassword()
        );
    }
    public UserEntity toEntity(User user ){
        if(user==null) return null;
        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
