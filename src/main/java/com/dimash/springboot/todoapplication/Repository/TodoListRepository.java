package com.dimash.springboot.todoapplication.Repository;

import com.dimash.springboot.todoapplication.Model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TodoListRepository extends JpaRepository<TodoList,Long> {

    public List<TodoList> findByPersonId(Long id);

    public void deleteTodoListById(Long id);

    public List<TodoList> findByIdAndCreationDate(Long id, LocalDate creationDate);
}
