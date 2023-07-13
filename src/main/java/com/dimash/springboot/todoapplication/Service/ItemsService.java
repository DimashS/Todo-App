package com.dimash.springboot.todoapplication.Service;

import com.dimash.springboot.todoapplication.Model.Items;
import com.dimash.springboot.todoapplication.Model.TodoList;
import com.dimash.springboot.todoapplication.Repository.ItemsRepository;
import com.dimash.springboot.todoapplication.Repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ItemsService {
    @Autowired
    private final ItemsRepository itemsRepository;
    @Autowired
    private final TodoListRepository todoListRepository;

    public ItemsService(ItemsRepository itemsRepository, TodoListRepository todoListRepository) {
        this.itemsRepository = itemsRepository;
        this.todoListRepository = todoListRepository;
    }

    public Items createItemForTodo(Long todoListId, Items items) {
        TodoList todoList = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new IllegalArgumentException("Not found todoList" + todoListId));
        items.setTodoList(todoList);
        return itemsRepository.save(items);
    }

    public List<Items> getItemsFromTodoList(Long todoListId, LocalDate date) {
        TodoList todoList = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new IllegalArgumentException("Incorrect id" + todoListId));
        try {
            return itemsRepository.getItemsByTodoListAndCreatedDate(todoList, date);
        } catch (Exception e) {
            throw new IllegalArgumentException("Incorrect date use pattern: dd:MM:yyyy");
        }
    }

    public Items updateItemForTodoList(Long itemId, Items updatedItems) {
        Items items1 = itemsRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Not found todoList" + itemId));
        items1.setDescription(updatedItems.getDescription());
        items1.setCompleted(updatedItems.getCompleted());
        items1.setCreatedDate(updatedItems.getCreatedDate());
        items1.setModifiedDate(LocalTime.from(LocalDate.now()));
        return itemsRepository.save(items1);
    }

    public boolean deleteItems(Long itemsId) {
        if (itemsRepository.existsById(itemsId)) {
            itemsRepository.deleteById(itemsId);
            return true;
        } else {
            return false;
        }
    }


    // приходит запрос вида


//    public Items createNewItems(Items items) {
//        return itemsRepository.save(items);
//    }
//
//    public Items getItemByDescription(String description) {
//        return itemsRepository.findByDescription(description);
//    }
//
//    public Items getById(Long id) {
//        return itemsRepository.getById(id);
//    }
//
//    public List<Items> getAllItems() {
//        return itemsRepository.findAllBy();
//    }
//
//    public List<Items> getAllCompletedItems() {
//        return itemsRepository.findByCompletedTrue();
//    }
//
//    public List<Items> getAllCompletedFalseItems() {
//        return itemsRepository.findByCompletedFalse();
//    }
//
//    public List<Items> getByIdAndCreationDate(Instant instant) {
//        return itemsRepository.getItemsByTodoListAndCreatedDate(instant);
//    }
//
//    public Items updateItems(Items items) {
//        return itemsRepository.save(items);
//    }
//
//    public void deleteItems(Long id) {
//        itemsRepository.deleteItemsById(id);
//    }
}
