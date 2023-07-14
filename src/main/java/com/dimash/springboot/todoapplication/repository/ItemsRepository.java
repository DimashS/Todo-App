package com.dimash.springboot.todoapplication.Repository;

import com.dimash.springboot.todoapplication.Model.Item;
import com.dimash.springboot.todoapplication.Model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Long> {
     List<Item> getItemsByTodoListAndDate(TodoList todoList, LocalDate date);
     List<Item> findItemsByTodoListId(Long id);
}