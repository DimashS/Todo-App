package com.dimash.springboot.todoapplication.service;

public interface TodoListAccessService {

    boolean canUpdate(Long todoListId);

    boolean canDelete(Long todoListId);
}
