package com.dimash.springboot.todoapplication.service;

import com.dimash.springboot.todoapplication.model.Item;

import java.time.LocalDate;
import java.util.List;

public interface ItemService {
    Item create(Long todoListId, Item item);

    List<Item> get(Long todoListId, LocalDate date);

    List<Item> get(Long todoListId, String description);

    Item update(Long itemId, Item updatedItem);

    boolean delete(Long itemId);

    Item getItem(Long itemId);
}
