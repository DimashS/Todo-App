package com.dimash.springboot.todoapplication.controller;

import com.dimash.springboot.todoapplication.model.Item;
import com.dimash.springboot.todoapplication.service.ItemsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/item")
public class AppControllerForItems {

    private final ItemsService itemsService;

    public AppControllerForItems(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @GetMapping("/date")
    public ResponseEntity<List<Item>> getItem(@RequestParam(value = "todoListId") Long todoListId,
                                              @RequestParam(value = "date", required = false) LocalDate localDate) {
        // if we have required = false, would have we get troubles with services methods
        List<Item> itemsList = itemsService.getItem(todoListId, localDate);
        return ResponseEntity.ok(itemsList);
    }

    @GetMapping("/description")
    public ResponseEntity<List<Item>> getItem(@RequestParam(value = "todoListId") Long todoListId,
                                              @RequestParam(value = "description") String description) {
        List<Item> item = itemsService.getByDescription(todoListId, description);
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestParam(value = "todoListId") Long todoListId, @RequestBody Item items) {
        Item createdItem = itemsService.createItem(todoListId, items);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Item> updateItem(@PathVariable Long itemId, @RequestBody Item updatedItem) {
        Item updatedItems = itemsService.update(itemId, updatedItem);
        return ResponseEntity.ok(updatedItems);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        boolean deleted = itemsService.delete(itemId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

