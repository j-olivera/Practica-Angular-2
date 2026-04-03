# Proposal: Login con JWT y protección de API

## Intent

Permitir que un usuario autentique con email/contraseña y reciba un **JWT** para acceder a rutas `/api/**` (excepto registro/login), alineado con hexagonal y el registro existente (BCrypt).

## Scope

### In Scope

- Endpoint `POST /api/auth/login` y caso de uso en `application`.
- Puerto de salida para emisión/validación de JWT; filtro HTTP que rellena el contexto de seguridad.
- Spring Security: cadena stateless, rutas públicas (auth + OpenAPI), resto de `/api/**` con JWT válido.
- Propiedades configurables: secreto y expiración del token.
- Extensión de `PasswordEncoderPort` con verificación para login.

### Out of Scope

- Refresh tokens, revocación, roles/granularidad por recurso.
- Cambiar el modelo de dominio `User` o flujo de registro.

## Capabilities

### New Capabilities

- None (sigue `user-auth`).

### Modified Capabilities

- `user-auth`: añade login JWT, validación de credenciales y protección de API.

## Approach

`LoginUseCase` + `JwtTokenPort`; adaptador JJWT; `SecurityFilterChain` + `JwtAuthenticationFilter`; excepción de dominio → 401 genérico.

## Affected Areas

| Area | Impact |
|------|--------|
| `pom.xml` | `spring-boot-starter-security`, JJWT |
| `application.properties` | `jwt.secret`, `jwt.expiration-ms` |
| `core/exceptions` | `InvalidCredentialsException` |
| `application` | DTOs, puertos, `LoginUseCaseImpl` |
| `infrastructure` | `SecurityConfig`, filtro JWT, adaptador JWT |
| `SwaggerConfig` | Esquema Bearer |

## Risks

| Risk | Mitigation |
|------|------------|
| Secreto débil en dev | Documentar; mínimo 32 bytes para HS256 |
| Usuarios legacy sin BCrypt | No coinciden `matches`; documentar |

## Rollback Plan

Quitar dependencias, clases de seguridad/JWT y propiedades; restaurar `pom` y `PasswordEncoderPort` sin `matches`.

## Success Criteria

- [ ] Login correcto devuelve 200 + JWT; credenciales incorrectas 401 sin filtrar detalle.
- [ ] `GET /api/users/{id}` sin token → 401; con token válido → comportamiento actual.
- [ ] Registro y Swagger UI siguen accesibles sin JWT.
