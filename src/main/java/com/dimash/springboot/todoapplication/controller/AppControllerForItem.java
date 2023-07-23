package com.dimash.springboot.todoapplication.controller;

import com.dimash.springboot.todoapplication.dto.ItemDTO;
import com.dimash.springboot.todoapplication.model.Item;
import com.dimash.springboot.todoapplication.service.serviceImpl.ItemServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/item")
@PreAuthorize("isAuthenticated()")
public class AppControllerForItems {

    private final ItemServiceImpl itemService;

    private final ModelMapper modelMapper;

    public AppControllerForItems(ItemServiceImpl itemsService, ModelMapper modelMapper) {
        this.itemService = itemsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/date")
    public ResponseEntity<List<ItemDTO>> getItem(@RequestParam(value = "todoListId") Long todoListId,
                                                 @RequestParam(value = "required_date", required = false)
                                                 LocalDate requiredDate) {
        // if we have required = false, could we have troubles with services methods
        return ResponseEntity.ok(itemService.get(todoListId, requiredDate).stream()
                .map(this::convertToDTO).toList());
    }

    @GetMapping("/description")
    public ResponseEntity<List<ItemDTO>> getItem(@RequestParam(value = "todoListId") Long todoListId,
                                                 @RequestParam(value = "description") String description) {
         return ResponseEntity.ok(itemService.get(todoListId, description).stream()
                .map(this::convertToDTO).toList());
    }

    @PostMapping
    public ResponseEntity<ItemDTO> createItem(@RequestParam(value = "todoListId") Long todoListId, @RequestBody ItemDTO itemDTO) {
        ItemDTO newItemDTO = convertToDTO(itemService.create(todoListId, convertToEntity(itemDTO)));
        return ResponseEntity.status(HttpStatus.CREATED).body(newItemDTO);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Long itemId, @RequestBody ItemDTO updatedItem) {
        return ResponseEntity.ok(convertToDTO(itemService.update(itemId,convertToEntity(updatedItem))));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        boolean deleted = itemService.delete(itemId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ItemDTO convertToDTO(Item item) {
        return modelMapper.map(item, ItemDTO.class);
    }

    public Item convertToEntity(ItemDTO itemDTO) {
        return modelMapper.map(itemDTO, Item.class);
    }
}

