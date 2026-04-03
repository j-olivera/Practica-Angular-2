# Proposal: Propiedad de recursos vs JWT

## Intent

Alinear las operaciones de **usuarios, personajes y partidas** con el `userId` del JWT para impedir que un usuario autenticado manipule o lea datos de otro.

## Scope

- Casos de uso: pasan `requesterUserId` y validan propiedad o coincidencia de id.
- Controladores: `@AuthenticationPrincipal Long` desde el contexto de seguridad.
- Excepción `ForbiddenAccessException` → 403.
- Nuevo endpoint `GET /api/characters/user/{userId}` (listado por usuario, ya existía en el caso de uso).
- Items/Builds: sin cambio (catálogo compartido en el modelo actual).

## Success Criteria

- Perfil ajeno, personajes ajenos, partidas ajenas → 403.
- `mvn test` OK.
