package com.gestor.game.application.usecase.character;

import com.gestor.game.application.dto.character.CharacterRequest;
import com.gestor.game.application.dto.character.CharacterResponse;
import com.gestor.game.application.mappers.build.BuildMapper;
import com.gestor.game.application.mappers.character.CharacterMapper;
import com.gestor.game.application.mappers.user.UserMapper;
import com.gestor.game.application.port.in.character.CreateCharacterUseCase;
import com.gestor.game.application.port.out.build.BuildRepositoryPort;
import com.gestor.game.application.port.out.character.CharacterRepositoryPort;
import com.gestor.game.application.port.out.user.UserRepositoryPort;
import com.gestor.game.core.entities.build.Build;
import com.gestor.game.core.entities.user.User;
import com.gestor.game.core.exceptions.NameNotValidException;
import com.gestor.game.core.exceptions.build.BuildDontExistException;
import com.gestor.game.core.exceptions.user.UserDontExistException;
import com.gestor.game.core.entities.character.Character;

public class CreateCharacterUseCaseImpl implements CreateCharacterUseCase {
    private final CharacterRepositoryPort characterRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final BuildRepositoryPort buildRepositoryPort;
    private final CharacterMapper characterMapper;

    public CreateCharacterUseCaseImpl(CharacterRepositoryPort characterRepositoryPort, UserRepositoryPort userRepositoryPort, BuildRepositoryPort buildRepositoryPort,CharacterMapper characterMapper) {
        this.characterRepositoryPort = characterRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
        this.buildRepositoryPort = buildRepositoryPort;

        this.characterMapper = characterMapper;
    }


    @Override
    public CharacterResponse createCharacter(CharacterRequest characterRequest) {

        User user = userRepositoryPort.findById(characterRequest.userId())
                .orElseThrow(()-> new UserDontExistException("User don't exist"));

        Build build = buildRepositoryPort.findById(characterRequest.buildId())
                .orElseThrow(()-> new BuildDontExistException("Build don't exist"));

        if(characterRepositoryPort.existsByName(characterRequest.name())) {
            throw new NameNotValidException("Name already exists");
        }
        Character ch = characterMapper.toEntity(characterRequest,user,build);
        Character saved = characterRepositoryPort.save(ch);
        return characterMapper.toResponse(saved);
    }
}
