package com.gestor.game.application.usecase.user;

import com.gestor.game.application.dto.user.UserResponse;
import com.gestor.game.application.port.in.user.RetrieveUserUseCase;
import com.gestor.game.application.port.out.user.UserRepositoryPort;
import com.gestor.game.core.entities.user.User;
import com.gestor.game.core.exceptions.auth.ForbiddenAccessException;
import com.gestor.game.core.exceptions.user.UserDontExistException;
import com.gestor.game.application.mappers.user.UserMapper;

public class RetrieveUserUseCaseImpl implements RetrieveUserUseCase {
    private final UserRepositoryPort userRepositoryPort;
    private final UserMapper userMapper;

    public RetrieveUserUseCaseImpl(UserRepositoryPort userRepositoryPort, UserMapper userMapper) {
        this.userRepositoryPort = userRepositoryPort;
        this.userMapper = userMapper;
    }


    @Override
    public UserResponse getUserById(Long id, Long requesterUserId) {
        if (!id.equals(requesterUserId)) {
            throw new ForbiddenAccessException("You can only access your own user profile");
        }
        User user = userRepositoryPort.findById(id).orElseThrow(()-> new UserDontExistException("User does not exist"));
        return userMapper.toResponse(user);
    }
}
