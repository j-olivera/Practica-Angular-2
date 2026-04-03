# Design: Login con JWT

## Technical Approach

Se extiende `PasswordEncoderPort` con `matches(raw, encoded)` implementado en `BCryptPasswordEncoderAdapter`. Nuevo `JwtTokenPort` en `application.port.out.security` con `createAccessToken(userId, email)` y `Optional<Long> validateAndGetUserId(jwt)` para el filtro. `LoginUseCaseImpl` resuelve usuario por email, verifica contraseña y devuelve `LoginResponse` (token, tipo, `UserResponse`). Spring Security aplica `SecurityFilterChain` stateless: permitAll en `/api/auth/register`, `/api/auth/login`, rutas OpenAPI; `JwtAuthenticationFilter` antes de `UsernamePasswordAuthenticationFilter`; entry point 401.

## Architecture Decisions

### Decision: JJWT en adaptador de infraestructura

**Choice**: `io.jsonwebtoken` solo en `JwtTokenAdapter`.  
**Alternatives**: `spring-security-oauth2-resource-server` con claves propias.  
**Rationale**: Menos superficie de configuración OAuth2; HS256 suficiente para MVP.

### Decision: Principal = `Long userId` en `Authentication`

**Choice**: `UsernamePasswordAuthenticationToken(userId, null, emptyList())`.  
**Rationale**: Los controladores actuales usan `@PathVariable Long id`; futuro `@AuthenticationPrincipal` disponible.

### Decision: Misma excepción para usuario inexistente y password incorrecta

**Choice**: `InvalidCredentialsException` con mensaje genérico.  
**Rationale**: Reduce enumeración de cuentas.

## Files

| Action | Path |
|--------|------|
| Modify | `pom.xml` |
| Modify | `application.properties` |
| Modify | `PasswordEncoderPort`, `BCryptPasswordEncoderAdapter` |
| New | `core/exceptions/auth/InvalidCredentialsException.java` |
| New | `application/dto/auth/LoginRequest`, `LoginResponse` |
| New | `application/port/in/auth/LoginUseCase` |
| New | `application/port/out/security/JwtTokenPort` |
| New | `application/usecase/auth/LoginUseCaseImpl` |
| New | `infrastructure/config/SecurityConfig`, `JwtAuthenticationFilter` |
| New | `infrastructure/adapters/security/JwtTokenAdapter`, `JwtProperties` |
| Modify | `AuthController`, `BeanConfiguration`, `SwaggerConfig`, `GlobalExceptionHandler` |
