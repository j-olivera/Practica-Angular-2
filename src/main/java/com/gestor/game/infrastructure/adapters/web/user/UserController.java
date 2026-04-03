package com.gestor.game.infrastructure.adapters.web.user;

import com.gestor.game.application.dto.user.UserResponse;
import com.gestor.game.application.port.in.user.RetrieveUserUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final RetrieveUserUseCase retrieveUserUseCase;

    public UserController(RetrieveUserUseCase retrieveUserUseCase) {
        this.retrieveUserUseCase = retrieveUserUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> retrieveUser(
            @PathVariable Long id,
            @AuthenticationPrincipal Long authenticatedUserId) {
        UserResponse userResponse = retrieveUserUseCase.getUserById(id, authenticatedUserId);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
/*
¿Por qué sucede este error y por qué agregamos esto?
Por defecto, los navegadores web (Chrome, Firefox, etc.)
 tienen una medida de seguridad estricta llamada Política del Mismo Origen (Same-Origin Policy).
 Esta regla impide que una página web alojada en un lugar
 (tu Angular, que corre en http://localhost:4200) haga peticiones
 a un servidor alojado en otro lugar distinto
 (tu Spring Boot, que corre en http://localhost:8080).
 El navegador bloquea la respuesta por temor a que sea un ataque de un sitio malicioso.

Al agregar @CrossOrigin(origins = "http://localhost:4200"),
le estás diciendo a Spring Boot: "Oye, confía en las peticiones
que vengan específicamente de esta dirección". Spring Boot añadirá
 unas cabeceras especiales (headers) en su respuesta que le dirán
 al navegador que tod es seguro y legítimo.

¿Para qué sirve?
Para permitir la comunicación bidireccional fluida
entre tu cliente (Angular) y tu servidor (Spring Boot)
durante tu etapa de desarrollo. Sin esto, tu frontend
estará completamente ciego y no podrá leer ni enviar datos a tu base de datos.
 */