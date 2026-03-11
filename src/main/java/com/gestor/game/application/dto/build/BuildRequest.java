package com.gestor.game.application.dto.build;

public record BuildRequest(
        String name,
        Long swordId,
        Long armorId,
        Long mountId,
        Long blessingId
) {}
