package com.dimash.springboot.todoapplication.service.serviceImpl;

import com.dimash.springboot.todoapplication.model.Item;
import com.dimash.springboot.todoapplication.model.Person;
import com.dimash.springboot.todoapplication.model.TodoList;
import com.dimash.springboot.todoapplication.service.ItemAccessService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ItemAccessServiceImpl implements ItemAccessService {
    private final ItemServiceImpl itemServiceImpl;
    private final TodoListServiceImpl todoListServiceImpl;

    public ItemAccessServiceImpl(ItemServiceImpl itemServiceImpl, TodoListServiceImpl todoListServiceImpl) {
        this.itemServiceImpl = itemServiceImpl;
        this.todoListServiceImpl = todoListServiceImpl;
    }

    @Override
    public boolean canRead(Long todoListId) {
        TodoList todoList = todoListServiceImpl.getList(todoListId);
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return todoList.getPerson().getUsername().equals(person.getUsername());
    }

    @Override
    public boolean canCreate(Long todoListId) {
        return canRead(todoListId);
    }

    @Override
    public boolean canUpdate(Long itemId) {
        Item item = itemServiceImpl.getItem(itemId);
        TodoList todoList = item.getTodoList();
        TodoList todoList1 = todoListServiceImpl.getList(todoList.getId());
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return todoList1.getPerson().getUsername().equals(person.getUsername());
    }

    @Override
    public boolean canDelete(Long itemId) {
        return canUpdate(itemId);
    }
}
