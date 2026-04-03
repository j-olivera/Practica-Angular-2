# Verify report: user-login-jwt

## Spec alignment

| Requirement | Evidence |
|-------------|----------|
| Login + JWT | `POST /api/auth/login`, `LoginUseCaseImpl`, `JwtTokenPort` / `JwtTokenAdapter` (HS256) |
| 401 credenciales | `InvalidCredentialsException` → `GlobalExceptionHandler` |
| Rutas protegidas | `SecurityConfig`: `/api/**` authenticated except register/login; `JwtAuthenticationFilter` |
| Público auth + Swagger | `permitAll` en `/api/auth/register`, `/api/auth/login`, `/v3/api-docs*`, `/swagger-ui/**` |
| Registro sin JWT | Sin cambio funcional; sigue `permitAll` |

## Build

- `mvn test`: OK (tras excluir `UserDetailsServiceAutoConfiguration` para evitar usuario en memoria por defecto).

## Revisión técnica (hallazgos)

1. **Secreto JWT**: `application.properties` incluye valor de desarrollo; en producción usar variable de entorno y secreto ≥ 32 caracteres (validado en `JwtTokenAdapter`).
2. **Usuarios creados con `/api/users/create`**: contraseña en claro no pasará `BCryptPasswordEncoder.matches`; solo cuentas registradas vía `/api/auth/register` (o migración manual a BCrypt) podrán hacer login.
3. **Principal en seguridad**: el filtro establece `Authentication` con principal `Long userId`; los controladores actuales no lo usan aún (no hay `@AuthenticationPrincipal` ni comprobación de que el `id` del path coincida con el token).
4. **CORS**: `SecurityConfig` replica origen `http://localhost:4200` alineado con `@CrossOrigin` de controladores.

## Deviaciones del diseño

- Ninguna relevante.
