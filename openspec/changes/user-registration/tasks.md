# Tasks: Registro público de usuario

## Phase 1: Dependencias y puerto de codificación

- [x] 1.1 Añadir dependencia `spring-security-crypto` en `pom.xml`
- [x] 1.2 Crear `PasswordEncoderPort` y `BCryptPasswordEncoderAdapter`; registrar bean en `BeanConfiguration`

## Phase 2: Caso de uso y API

- [x] 2.1 Crear `RegisterUserRequest`, `RegisterUserUseCase`, `RegisterUserUseCaseImpl` y método en `UserMapper` para materializar `User` con password ya procesada
- [x] 2.2 Crear `AuthController` con `POST /api/auth/register` (CORS coherente con `UserController`)
- [x] 2.3 Registrar bean `RegisterUserUseCaseImpl` en `BeanConfiguration`

## Phase 3: Verificación

- [x] 3.1 Ejecutar `mvn test` y comprobar compilación
