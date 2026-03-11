package com.gestor.game.application.dto.item;

import com.gestor.game.core.enums.item.Category;

public record ItemResponse(
        Long id,
        String name,
        int statisticPoint,
        Category category
) {}
