# Proposal: Registro público de usuario

## Intent

Exponer un flujo de **autoregistro** vía API REST alineado con la arquitectura hexagonal, persistiendo credenciales de forma segura (hash) y dejando listo el contexto para **login JWT** en un cambio posterior.

## Scope

### In Scope

- Endpoint HTTP de registro bajo prefijo de autenticación.
- Caso de uso y puerto de entrada en `application`; codificación de contraseña vía puerto de salida (sin acoplar el dominio a Spring Security).
- Especificación OpenSpec y diseño técnico en `openspec/changes/user-registration/`.

### Out of Scope

- Login, emisión o validación de JWT.
- Cambiar o eliminar `POST /api/users/create` (sigue existiendo).
- OAuth2, verificación de email, captcha.

## Capabilities

### New Capabilities

- `user-auth`: Registro público de cuenta (API y reglas de negocio asociadas al alta de usuario autenticable).

### Modified Capabilities

- None

## Approach

Nuevo `RegisterUserUseCase` que reutiliza `UserRepositoryPort` y `UserMapper`, añade `PasswordEncoderPort` (adaptador BCrypt en infraestructura) y un `AuthController` con `POST /api/auth/register`.

## Affected Areas

| Area | Impact | Description |
|------|--------|-------------|
| `application/port/in` | New | `RegisterUserUseCase` |
| `application/port/out/security` | New | `PasswordEncoderPort` |
| `application/usecase/user` | New | `RegisterUserUseCaseImpl` |
| `application/dto/user` | New | `RegisterUserRequest` |
| `application/mappers/user` | Modified | Método para crear entidad desde datos ya validados |
| `infrastructure/adapters/web/auth` | New | `AuthController` |
| `infrastructure/adapters/security` | New | Adaptador BCrypt |
| `infrastructure/config` | Modified | Bean `RegisterUserUseCase` y encoder |
| `pom.xml` | Modified | `spring-security-crypto` |

## Risks

| Risk | Likelihood | Mitigation |
|------|------------|------------|
| Dos rutas de alta de usuario con distinto tratamiento de password | Med | Documentar; login JWT usará la ruta de registro + hash. |

## Rollback Plan

Revertir commit o eliminar paquete `auth`/caso de uso/puerto encoder y dependencia Maven; BD sin migración estructural nueva.

## Dependencies

- MySQL disponible; esquema actual de `User` sin cambios.

## Success Criteria

- [ ] `POST /api/auth/register` crea usuario y responde 201 con id, name, email (sin password).
- [ ] Email duplicado responde 409; validación de dominio existente responde 400.
- [ ] Contraseña persistida como hash BCrypt.
