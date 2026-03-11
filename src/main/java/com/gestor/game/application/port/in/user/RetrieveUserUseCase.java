package com.gestor.game.application.port.in.user;

import com.gestor.game.application.dto.user.UserRequest;
import com.gestor.game.application.dto.user.UserResponse;

public interface RetrieveUserUseCase {
    UserResponse getUserById(Long id);
}
