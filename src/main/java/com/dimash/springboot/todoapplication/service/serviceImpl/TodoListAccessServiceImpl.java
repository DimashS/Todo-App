package com.dimash.springboot.todoapplication.service.serviceImpl;

import com.dimash.springboot.todoapplication.model.TodoList;
import com.dimash.springboot.todoapplication.service.TodoListAccessService;
import com.dimash.springboot.todoapplication.service.TodoListService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TodoListAccessServiceImpl implements TodoListAccessService {
    private final TodoListService todoListService;

    public TodoListAccessServiceImpl(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    public boolean canRead(Long todoListId, Authentication authentication) {
        TodoList todoList = todoListService.getList(todoListId);
        return todoList != null && todoList.getPerson().getUsername().equals(authentication.getName());
    }

    public boolean canRead(org.springframework.security.core.Authentication authentication, LocalDate localDate) {
        return false;
    }


    public boolean canRead(Authentication authentication, String name) {
        return false;
    }

    public boolean canCreate(Authentication authentication, TodoList todoList) {
        return false;
    }

    public boolean canUpdate(Authentication authentication, Long todoListId, TodoList updatedTodoList) {
        return false;
    }

    public boolean canDelete(Authentication authentication, Long id) {
        return false;
    }
}
