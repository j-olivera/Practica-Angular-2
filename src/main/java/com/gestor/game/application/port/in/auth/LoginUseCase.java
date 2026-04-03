package com.gestor.game.application.port.in.auth;

import com.gestor.game.application.dto.auth.LoginRequest;
import com.gestor.game.application.dto.auth.LoginResponse;

public interface LoginUseCase {

    LoginResponse login(LoginRequest request);
}
