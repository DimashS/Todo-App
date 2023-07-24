package com.dimash.springboot.todoapplication.service;

import org.springframework.security.core.Authentication;

public interface ItemAccessService {
    boolean canRead(Long todoListId);
    boolean canCreate(Long todoListId);
    boolean canUpdate(Long itemId);
    boolean canDelete(Long itemId);
}
