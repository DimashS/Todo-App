package com.dimash.springboot.todoapplication.service;

public interface ItemAccessService {
    boolean canRead(Long todoListId);
    boolean canCreate(Long todoListId);
    boolean canUpdate(Long itemId);
    boolean canDelete(Long itemId);
}
