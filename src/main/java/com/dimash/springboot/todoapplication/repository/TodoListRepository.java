package com.dimash.springboot.todoapplication.repository;

import com.dimash.springboot.todoapplication.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {

    List<TodoList> findByPersonId(Long id);

    List<TodoList> findByIdAndCreationDate(Long id, LocalDate creationDate);

    List<TodoList> findTodoListByIdAndNameLike(Long id, String name);

}
