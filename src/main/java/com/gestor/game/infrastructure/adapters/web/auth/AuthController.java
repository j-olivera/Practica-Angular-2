package com.gestor.game.infrastructure.adapters.web.auth;

import com.gestor.game.application.dto.auth.LoginRequest;
import com.gestor.game.application.dto.auth.LoginResponse;
import com.gestor.game.application.dto.user.RegisterUserRequest;
import com.gestor.game.application.dto.user.UserResponse;
import com.gestor.game.application.port.in.auth.LoginUseCase;
import com.gestor.game.application.port.in.user.RegisterUserUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirements
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase, LoginUseCase loginUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterUserRequest request) {
        UserResponse body = registerUserUseCase.register(request);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse body = loginUseCase.login(request);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
