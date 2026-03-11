package com.gestor.game.application.dto.build;

import com.gestor.game.application.dto.item.ItemResponse;

public record BuildResponse(
        Long id,
        String name,
        ItemResponse sword,
        ItemResponse armor,
        ItemResponse mount,
        ItemResponse blessing
) {}
