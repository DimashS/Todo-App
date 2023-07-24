package com.dimash.springboot.todoapplication.service.serviceImpl;

import com.dimash.springboot.todoapplication.model.Person;
import com.dimash.springboot.todoapplication.model.TodoList;
import com.dimash.springboot.todoapplication.service.TodoListAccessService;
import com.dimash.springboot.todoapplication.service.TodoListService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TodoListAccessServiceImpl implements TodoListAccessService {
    private final TodoListService todoListService;

    public TodoListAccessServiceImpl(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    public boolean canUpdate(Long todoListId) {
        TodoList todoList = todoListService.getList(todoListId);
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return todoList != null && todoList.getPerson().getId().equals(person.getId());
    }

    @Override
    public boolean canDelete(Long todoListId) {
        TodoList todoList = todoListService.getList(todoListId);
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return todoList.getPerson().getUsername().equals(person.getUsername());
    }

}
