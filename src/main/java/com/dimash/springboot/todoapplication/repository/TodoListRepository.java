package com.dimash.springboot.todoapplication.repository;

import com.dimash.springboot.todoapplication.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {

    List<TodoList> findByPersonId(Long id);

    List<TodoList> findByIdAndCreatedDate(Long id, LocalDateTime createdDate);

    List<TodoList> findTodoListByIdAndNameLike(Long id, String name);

}
