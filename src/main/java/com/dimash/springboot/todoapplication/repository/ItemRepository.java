package com.dimash.springboot.todoapplication.repository;

import com.dimash.springboot.todoapplication.model.Item;
import com.dimash.springboot.todoapplication.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> getItemsByTodoListAndDate(TodoList todoList, LocalDate date);

    List<Item> findItemsByTodoListId(Long id);

    List<Item> findItemsByIdAndDescriptionLike(Long id, String description);
}