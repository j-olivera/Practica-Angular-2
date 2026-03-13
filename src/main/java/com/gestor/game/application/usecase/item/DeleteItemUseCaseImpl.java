package com.gestor.game.application.usecase.item;

import com.gestor.game.application.port.in.item.DeleteItemUseCase;
import com.gestor.game.application.port.out.item.ItemRepositoryPort;
import com.gestor.game.core.entities.item.Item;
import com.gestor.game.core.exceptions.item.ItemNotFoundException;

public class DeleteItemUseCaseImpl implements DeleteItemUseCase {
    private final ItemRepositoryPort itemRepositoryPort;

    public DeleteItemUseCaseImpl(ItemRepositoryPort itemRepositoryPort) {
        this.itemRepositoryPort = itemRepositoryPort;
    }

    @Override
    public void deleteItem(Long id) {
        Item item = itemRepositoryPort.findById(id).orElseThrow(()-> new ItemNotFoundException("Item not found"));
        itemRepositoryPort.delete(item.getId());
    }
}
