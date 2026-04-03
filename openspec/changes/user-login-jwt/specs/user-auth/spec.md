# Delta for user-auth

## ADDED Requirements

### Requirement: Login with JWT issuance

The system MUST expose an HTTP endpoint that accepts email and password and, when credentials match a stored user whose password is verifiable with the configured encoder, responds with a signed JWT and basic user identity without exposing the password hash.

#### Scenario: Successful login

- GIVEN a registered user with a verifiable password hash
- WHEN the client sends correct email and password to the login endpoint
- THEN the system MUST respond with 200 OK
- AND the body MUST include a non-empty access token and token type `Bearer`
- AND the body MUST include the user id, name, and email (no password)

#### Scenario: Invalid credentials

- GIVEN the email does not exist OR the password does not match the stored hash
- WHEN the client calls the login endpoint
- THEN the system MUST respond with 401 Unauthorized
- AND MUST NOT indicate whether the email or the password was wrong

### Requirement: JWT access to protected API routes

The system MUST require a valid `Authorization: Bearer <JWT>` header for HTTP requests under `/api/**` except the public authentication routes and OpenAPI/Swagger paths configured for documentation.

#### Scenario: Request without token to protected route

- GIVEN no valid JWT is provided
- WHEN the client calls a protected `/api/**` route
- THEN the system MUST respond with 401 Unauthorized

#### Scenario: Request with valid JWT

- GIVEN a JWT issued by the system and not expired
- WHEN the client calls a protected `/api/**` route with `Authorization: Bearer <JWT>`
- THEN the system MUST treat the request as authenticated for that user identity

#### Scenario: Public auth routes remain open

- GIVEN the client does not send a JWT
- WHEN the client calls user registration or login endpoints
- THEN the system MUST NOT require prior authentication

## MODIFIED Requirements

### Requirement: Public registration endpoint

The system MUST expose an HTTP endpoint that allows a new user to register with name, email, and password.

#### Scenario: Successful registration

- GIVEN no user exists with the submitted email
- WHEN the client sends valid name, email, and password to the registration endpoint
- THEN the system MUST respond with 201 Created
- AND the body MUST include the new user id, name, and email
- AND the body MUST NOT include the password
- AND the stored password MUST be a one-way hash (not plaintext)

#### Scenario: Duplicate email

- GIVEN a user already exists with the same email
- WHEN the client submits registration with that email
- THEN the system MUST respond with 409 Conflict
- AND no duplicate user record MUST be created

#### Scenario: Invalid domain data

- GIVEN name, email, or password violates existing domain validation rules
- WHEN the client submits registration
- THEN the system MUST respond with 400 Bad Request
- AND MUST NOT persist the user

(Previously: mismo requisito sin contexto de rutas protegidas; el registro sigue siendo público.)
