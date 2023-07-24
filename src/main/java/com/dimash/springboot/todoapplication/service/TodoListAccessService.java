package com.dimash.springboot.todoapplication.service;

import org.springframework.security.core.Authentication;

public interface TodoListAccessService {

    boolean canUpdate(Long todoListId);

    boolean canDelete(Long todoListId);
}
