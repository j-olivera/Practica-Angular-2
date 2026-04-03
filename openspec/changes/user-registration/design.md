# Design: Registro público de usuario

## Technical Approach

Se introduce el namespace REST `/api/auth` para operaciones de identidad. El registro delega en `RegisterUserUseCase`, que aplica las mismas reglas de unicidad de email que `CreateUserUseCase`, pero **obliga** a persistir la contraseña tras pasar por `PasswordEncoderPort` (implementación BCrypt en infraestructura). El dominio `User` sigue recibiendo un `String password` (en registro será el hash); no se filtra el secreto en `UserResponse`.

## Architecture Decisions

### Decision: Puerto `PasswordEncoderPort` en application

**Choice**: Interfaz en `application.port.out.security` implementada solo en infraestructura.  
**Alternatives considered**: Usar `BCryptPasswordEncoder` directamente en el caso de uso; hash en el controller.  
**Rationale**: Mantiene el núcleo de aplicación testeable y alineado con hexagonal; el detalle BCrypt queda en el adaptador.

### Decision: Caso de uso dedicado `RegisterUserUseCase`

**Choice**: Nuevo caso de uso en lugar de reutilizar solo `CreateUserUseCase` desde el controller.  
**Alternatives considered**: Reutilizar `CreateUserUseCase` inyectando encoder y cambiar comportamiento global.  
**Rationale**: Registro público queda explícito para evolución JWT; `POST /api/users/create` permanece sin cambios en este cambio.

### Decision: DTO `RegisterUserRequest`

**Choice**: Record propio (misma forma que `UserRequest` hoy).  
**Alternatives considered**: Reutilizar `UserRequest`.  
**Rationale**: Separa el contrato del módulo auth del CRUD de usuarios.

## Flow

1. `AuthController` recibe JSON → `RegisterUserRequest`.
2. `RegisterUserUseCaseImpl` comprueba `existsByEmail`.
3. Codifica password → `User.create` (validación existente) → `save` → `UserResponse`.

## Files

| Action | Path |
|--------|------|
| New | `application/port/in/user/RegisterUserUseCase.java` |
| New | `application/port/out/security/PasswordEncoderPort.java` |
| New | `application/dto/user/RegisterUserRequest.java` |
| New | `application/usecase/user/RegisterUserUseCaseImpl.java` |
| New | `infrastructure/adapters/web/auth/AuthController.java` |
| New | `infrastructure/adapters/security/BCryptPasswordEncoderAdapter.java` |
| Modify | `application/mappers/user/UserMapper.java` |
| Modify | `infrastructure/config/BeanConfiguration.java` |
| Modify | `pom.xml` |
