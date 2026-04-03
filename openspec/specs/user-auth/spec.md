# User authentication (registration)

## Purpose

Definir el comportamiento del **registro público** de usuarios antes del login con JWT.

## Requirements

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
