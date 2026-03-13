package com.gestor.game.application.usecase.character;

import com.gestor.game.application.dto.character.CharacterResponse;
import com.gestor.game.application.mappers.character.CharacterMapper;
import com.gestor.game.application.port.in.character.RetrieveCharacterUseCase;
import com.gestor.game.application.port.out.character.CharacterRepositoryPort;
import com.gestor.game.application.port.out.user.UserRepositoryPort;
import com.gestor.game.core.entities.character.Character;
import com.gestor.game.core.entities.user.User;
import com.gestor.game.core.exceptions.character.CharacterDoesNotExistException;
import com.gestor.game.core.exceptions.user.UserDontExistException;

import java.util.List;

public class RetrieveCharacterUseCaseImpl implements RetrieveCharacterUseCase {
    private final CharacterRepositoryPort characterRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final CharacterMapper characterMapper;

    public RetrieveCharacterUseCaseImpl(CharacterRepositoryPort characterRepositoryPort, UserRepositoryPort userRepositoryPort, CharacterMapper characterMapper) {
        this.characterRepositoryPort = characterRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
        this.characterMapper = characterMapper;
    }


    @Override
    public CharacterResponse getCharacterById(Long id) {
        Character character = characterRepositoryPort.findById(id)
                .orElseThrow(()-> new CharacterDoesNotExistException("Character doesn't exist"));

        return characterMapper.toResponse(character);
    }

    @Override
    public List<CharacterResponse> getCharactersByUserId(Long userId) {
        //verificación de usurio existente
        User user  = userRepositoryPort.findById(userId)
                .orElseThrow(()-> new UserDontExistException("User with id "+ userId +" doesn't exist"));

        List<Character> characters = characterRepositoryPort.findByUserId(user.getId());

        if (characters.isEmpty()) {
            throw new CharacterDoesNotExistException("User with id " + user.getId() + " don't have any characters");
        }
        return List.of();
    }
}
