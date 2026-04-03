package com.gestor.game.application.usecase.auth;

import com.gestor.game.application.dto.auth.LoginRequest;
import com.gestor.game.application.dto.auth.LoginResponse;
import com.gestor.game.application.mappers.user.UserMapper;
import com.gestor.game.application.port.in.auth.LoginUseCase;
import com.gestor.game.application.port.out.security.JwtTokenPort;
import com.gestor.game.application.port.out.security.PasswordEncoderPort;
import com.gestor.game.application.port.out.user.UserRepositoryPort;
import com.gestor.game.core.entities.user.User;
import com.gestor.game.core.exceptions.auth.InvalidCredentialsException;

public class LoginUseCaseImpl implements LoginUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final JwtTokenPort jwtTokenPort;
    private final UserMapper userMapper;

    public LoginUseCaseImpl(
            UserRepositoryPort userRepositoryPort,
            PasswordEncoderPort passwordEncoderPort,
            JwtTokenPort jwtTokenPort,
            UserMapper userMapper) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.jwtTokenPort = jwtTokenPort;
        this.userMapper = userMapper;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepositoryPort.findByEmail(request.email())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
        if (!passwordEncoderPort.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        String token = jwtTokenPort.createAccessToken(user.getId(), user.getEmail());
        return new LoginResponse(token, "Bearer", userMapper.toResponse(user));
    }
}
