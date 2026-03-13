package com.gestor.game.application.usecase.user;

import com.gestor.game.application.dto.user.UserRequest;
import com.gestor.game.application.dto.user.UserResponse;
import com.gestor.game.application.port.in.user.CreateUserUseCase;
import com.gestor.game.application.port.out.user.UserRepositoryPort;
import com.gestor.game.core.entities.user.User;
import com.gestor.game.core.exceptions.user.UserAlreadyExistException;
import com.gestor.game.application.mappers.user.UserMapper;

public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final UserMapper userMapper;

    public CreateUserUseCaseImpl(UserRepositoryPort userRepositoryPort, UserMapper userMapper) {
        this.userRepositoryPort = userRepositoryPort;
        this.userMapper = userMapper;
    }


    @Override
    public UserResponse createUser(UserRequest userRequest) {
        // La regla para distinguirlos es simple: si la regla necesita consultar
        // algo externo (base de datos, otro servicio), va en el use case.
        // Si es una regla propia del objeto que siempre se cumple sin importar el contexto, va en la entidad.

        if(userRepositoryPort.existsByEmail(userRequest.email())){
            throw new UserAlreadyExistException("Email already exist");
        }
        User user = userMapper.toEntity(userRequest);
        User saveUser = userRepositoryPort.save(user);

        return userMapper.toResponse(saveUser);
    }
}
