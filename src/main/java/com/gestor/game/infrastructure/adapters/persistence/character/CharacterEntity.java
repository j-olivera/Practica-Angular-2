package com.gestor.game.infrastructure.adapters.persistence.character;

import com.gestor.game.core.enums.character.WarriorClass;
import com.gestor.game.infrastructure.adapters.persistence.build.BuildEntity;
import com.gestor.game.infrastructure.adapters.persistence.user.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "characters")
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  nullable = false)
    private UserEntity userEntity;
    @Column
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "build_id",  nullable = false)
    private BuildEntity buildEntity;
    @Enumerated(EnumType.STRING)
    @Column(name = "warrior_class", nullable = false)
    private WarriorClass warriorClass;

    public CharacterEntity(Long id, UserEntity userEntity, String name, BuildEntity buildEntity, WarriorClass warriorClass) {
        this.id = id;
        this.userEntity = userEntity;
        this.name = name;
        this.buildEntity = buildEntity;
        this.warriorClass = warriorClass;
    }

    public CharacterEntity() {}

    public Long getId() {
        return id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public String getName() {
        return name;
    }

    public BuildEntity getBuildEntity() {
        return buildEntity;
    }

    public WarriorClass getWarriorClass() {
        return warriorClass;
    }
}
