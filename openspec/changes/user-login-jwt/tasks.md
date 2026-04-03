# Tasks: Login JWT

## Phase 1: Dependencias y propiedades

- [x] 1.1 Añadir `spring-boot-starter-security` y JJWT (api, impl, jackson); quitar `spring-security-crypto` suelto si queda cubierto
- [x] 1.2 Añadir `jwt.secret` y `jwt.expiration-ms` en `application.properties`

## Phase 2: Dominio y aplicación

- [x] 2.1 `InvalidCredentialsException`; extender `PasswordEncoderPort` con `matches`
- [x] 2.2 `JwtTokenPort`, `JwtTokenAdapter`, `JwtProperties`; `LoginRequest`, `LoginResponse`, `LoginUseCase`, `LoginUseCaseImpl`
- [x] 2.3 Registrar beans en `BeanConfiguration`

## Phase 3: Seguridad HTTP

- [x] 3.1 `SecurityConfig` (stateless, CSRF off, permit auth + swagger, `/api/**` authenticated) y `JwtAuthenticationFilter`
- [x] 3.2 `AuthController` `POST /login`; `GlobalExceptionHandler` → 401 para credenciales inválidas
- [x] 3.3 `SwaggerConfig`: esquema Bearer; `AuthController` sin requisito de seguridad global en operaciones públicas

## Phase 4: Verificación

- [x] 4.1 `mvn test`; revisión estática (secretos, rutas, hexagonal)
