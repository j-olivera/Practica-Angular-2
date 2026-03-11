package com.gestor.game.application.dto.item;

import com.gestor.game.core.enums.item.Category;

import java.time.LocalDateTime;

public record ItemRequest(
        String name,
        LocalDateTime date, // ver si luego se declara automaticamente en el core
        int statisticPoint,
        Category category
) {}
