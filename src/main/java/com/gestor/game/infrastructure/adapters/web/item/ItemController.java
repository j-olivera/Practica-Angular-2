package com.gestor.game.infrastructure.adapters.web.item;

import com.gestor.game.application.dto.item.ItemRequest;
import com.gestor.game.application.dto.item.ItemResponse;
import com.gestor.game.application.port.in.item.CreateItemUseCase;
import com.gestor.game.application.port.in.item.DeleteItemUseCase;
import com.gestor.game.application.port.in.item.RetrieveItemUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "http://localhost:4200")
@SecurityRequirement(name = "bearerAuth")
public class ItemController {
    private final CreateItemUseCase createItemUseCase;
    private final DeleteItemUseCase deleteItemUseCase;
    private final RetrieveItemUseCase retrieveItemUseCase;

    public ItemController(CreateItemUseCase createItemUseCase, DeleteItemUseCase deleteItemUseCase, RetrieveItemUseCase retrieveItemUseCase) {
        this.createItemUseCase = createItemUseCase;
        this.deleteItemUseCase = deleteItemUseCase;
        this.retrieveItemUseCase = retrieveItemUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemRequest itemRequest) {
        ItemResponse itemResponse = createItemUseCase.createItem(itemRequest);
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable Long id) {
        ItemResponse itemResponse = retrieveItemUseCase.getItemById(id);
        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ItemResponse> deleteItem(@PathVariable Long id) {
        deleteItemUseCase.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
