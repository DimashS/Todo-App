package com.dimash.springboot.todoapplication.Service;

import com.dimash.springboot.todoapplication.Model.Item;
import com.dimash.springboot.todoapplication.Model.TodoList;
import com.dimash.springboot.todoapplication.Repository.ItemsRepository;
import com.dimash.springboot.todoapplication.Repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Item createItem(Long todoListId, Item items) {
        TodoList todoList = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new RuntimeException("Not found todoList" + todoListId));
        items.setTodoList(todoList);
        return itemsRepository.save(items);
    }

    public List<Item> getItem(Long todoListId, LocalDate date) {
        TodoList todoList = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new RuntimeException("Incorrect id" + todoListId));
        // if null do in try, if not get all
        if (date != null) {
            return itemsRepository.getItemsByTodoListAndDate(todoList, date);
        }
        return itemsRepository.findItemsByTodoListId(todoListId);
    }

    public Item update(Long itemId, Item updatedItems) {
        Item items1 = itemsRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Not found todoList" + itemId));
        items1.setDescription(updatedItems.getDescription());
        items1.setCompleted(updatedItems.getCompleted());
        items1.setDate(updatedItems.getDate());
        items1.setTime(updatedItems.getTime());
        return itemsRepository.save(items1);
    }

    public boolean delete(Long itemsId) {
        if (itemsRepository.existsById(itemsId)) {
            itemsRepository.deleteById(itemsId);
            return true;
        } else {
            return false;
        }
    }
}
// приходит запрос вид