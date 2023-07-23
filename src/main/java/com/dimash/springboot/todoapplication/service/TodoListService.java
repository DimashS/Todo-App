package com.dimash.springboot.todoapplication.service;

import com.dimash.springboot.todoapplication.model.TodoList;

import java.time.LocalDateTime;
import java.util.List;

public interface TodoListService {
    TodoList create(Long personId, TodoList todoList);
    List<TodoList> get(Long personId, String name);
    List<TodoList> get(Long personId);
    List<TodoList> get(Long todoListId, LocalDateTime localDate);
    TodoList getList(Long todoListId);
    TodoList update(Long todoListId, TodoList updatedTodoList);
    void delete(Long id);
}
