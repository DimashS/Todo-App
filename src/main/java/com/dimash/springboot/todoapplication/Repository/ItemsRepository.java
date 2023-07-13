package com.dimash.springboot.todoapplication.Repository;

import com.dimash.springboot.todoapplication.Model.Items;
import com.dimash.springboot.todoapplication.Model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Long> {
    public List<Items> getItemsByTodoListAndCreatedDate(TodoList todoList, LocalDate createdDate);
}