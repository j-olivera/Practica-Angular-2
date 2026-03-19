package com.gestor.game.infrastructure.adapters.persistence.character;

import com.gestor.game.application.port.out.character.CharacterRepositoryPort;
import com.gestor.game.core.entities.character.Character;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class CharacterAdapterJpa implements CharacterRepositoryPort {

    private final CharacterRepositoryJpa characterRepository;
    private final CharacterMapperJpa characterMapper;

    public CharacterAdapterJpa(CharacterRepositoryJpa characterRepository, CharacterMapperJpa characterMapper) {
        this.characterRepository = characterRepository;
        this.characterMapper = characterMapper;
    }

    @Override
    public Character save(Character character) {
        CharacterEntity characterEntity = characterMapper.toJpaEntity(character);
        CharacterEntity save = characterRepository.save(characterEntity);
        return characterMapper.toCoreEntity(save);
    }

    @Override
    public Optional<Character> findById(Long id) {
        return characterRepository.findById(id).map(characterMapper::toCoreEntity);
    }

    @Override
    public List<Character> findByUserId(Long userId) {
        return characterRepository.findByUserEntity_Id(userId).stream().map(characterMapper::toCoreEntity).toList();
    }

    @Override
    public boolean existsByName(String name) {
        return characterRepository.existsByName(name);
    }

    @Override
    public void delete(Long id) {
        characterRepository.findById(id).ifPresent(characterRepository::delete);
    }
}
