package com.gestor.game.application.usecase.user;

import com.gestor.game.application.dto.user.RegisterUserRequest;
import com.gestor.game.application.dto.user.UserResponse;
import com.gestor.game.application.mappers.user.UserMapper;
import com.gestor.game.application.port.in.user.RegisterUserUseCase;
import com.gestor.game.application.port.out.security.PasswordEncoderPort;
import com.gestor.game.application.port.out.user.UserRepositoryPort;
import com.gestor.game.core.entities.user.User;
import com.gestor.game.core.exceptions.user.UserAlreadyExistException;

public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final UserMapper userMapper;
    private final PasswordEncoderPort passwordEncoderPort;

    public RegisterUserUseCaseImpl(
            UserRepositoryPort userRepositoryPort,
            UserMapper userMapper,
            PasswordEncoderPort passwordEncoderPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.userMapper = userMapper;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public UserResponse register(RegisterUserRequest request) {
        if (userRepositoryPort.existsByEmail(request.email())) {
            throw new UserAlreadyExistException("Email already exist");
        }
        String encodedPassword = passwordEncoderPort.encode(request.password());
        User user = userMapper.toNewUser(request.name(), request.email(), encodedPassword);
        User saved = userRepositoryPort.save(user);
        return userMapper.toResponse(saved);
    }
}
