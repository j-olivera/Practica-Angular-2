package com.gestor.game.application.port.in.user;

import com.gestor.game.application.dto.user.RegisterUserRequest;
import com.gestor.game.application.dto.user.UserResponse;

public interface RegisterUserUseCase {

    UserResponse register(RegisterUserRequest request);
}
