package com.gestor.game.application.usecase.character;

import com.gestor.game.application.port.in.character.DeleteCharacterUseCase;
import com.gestor.game.application.port.out.character.CharacterRepositoryPort;
import com.gestor.game.core.entities.character.Character;
import com.gestor.game.core.exceptions.auth.ForbiddenAccessException;
import com.gestor.game.core.exceptions.character.CharacterDoesNotExistException;

public class DeleteCharacterUseCaseImpl implements DeleteCharacterUseCase {
    private final CharacterRepositoryPort characterRepositoryPort;

    public DeleteCharacterUseCaseImpl(CharacterRepositoryPort characterRepositoryPort) {
        this.characterRepositoryPort = characterRepositoryPort;
    }

    @Override
    public void deleteCharacter(Long id, Long requesterUserId) {
        Character ch = characterRepositoryPort.findById(id)
                        .orElseThrow(()-> new CharacterDoesNotExistException("Character doesn't exist"));
        if (!ch.getUser().getId().equals(requesterUserId)) {
            throw new ForbiddenAccessException("You can only delete your own characters");
        }
        characterRepositoryPort.delete(ch.getId());
    }
}
