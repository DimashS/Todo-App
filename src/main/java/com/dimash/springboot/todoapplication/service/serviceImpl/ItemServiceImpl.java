package com.dimash.springboot.todoapplication.service;

import com.dimash.springboot.todoapplication.model.Item;
import com.dimash.springboot.todoapplication.model.TodoList;
import com.dimash.springboot.todoapplication.repository.ItemRepository;
import com.dimash.springboot.todoapplication.repository.TodoListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl {
    private final ItemRepository itemsRepository;
    private final TodoListRepository todoListRepository;

    public Item createItem(Long todoListId, Item item) {
        TodoList todoList = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new RuntimeException("Not found todoList " + todoListId));
        item.setTodoList(todoList);
        item.setCreatedDate(LocalDateTime.now());
        item.setLastModifiedDate(LocalDateTime.now());
        return itemsRepository.save(item);
    }

    public List<Item> getItem(Long todoListId, LocalDate date) {
        TodoList todoList = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new RuntimeException("Incorrect id " + todoListId));
        // if null do in try, if not get all
        if (date != null) {
            return itemsRepository.getItemsByTodoListAndDate(todoList, date);
        }
        return itemsRepository.findItemsByTodoListId(todoListId);
    }

    public List<Item> getItem(Long todoListId, String description) {
        TodoList todoList = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new RuntimeException("Incorrect id " + todoListId));
        return itemsRepository.findItemsByIdAndDescriptionLike(todoList.getId(), description);
    }

    public Item update(Long itemId, Item updatedItem) {
        Item items1 = itemsRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Not found todoList " + itemId));
        items1.setDescription(updatedItem.getDescription());
        items1.setCompleted(updatedItem.getCompleted());
        items1.setDate(updatedItem.getDate());
        items1.setTime(updatedItem.getTime());
        items1.setLastModifiedDate(LocalDateTime.now());
        return itemsRepository.save(items1);
    }

    public boolean delete(Long itemId) {
        if (itemsRepository.existsById(itemId)) {
            itemsRepository.deleteById(itemId);
            return true;
        } else {
            return false;
        }
    }
}
// приходит запрос вид