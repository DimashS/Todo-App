package com.dimash.springboot.todoapplication.service;

import com.dimash.springboot.todoapplication.model.TodoList;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

import java.time.LocalDate;

public interface TodoListAccessService {

    // getList(authentication,creationDate), getList(authentication,name), createList(authentication, todoList),
    // updateList(todoListId, updatedTodoList), deleteList(todoListId)
    boolean canRead(Authentication authentication, LocalDate localDate);
    boolean canRead(Authentication authentication, String name);
    boolean canCreate(Authentication authentication, TodoList todoList);

    boolean canUpdate(Authentication authentication, Long todoListId, TodoList updatedTodoList);

    boolean canDelete(Authentication authentication, Long id);
}
