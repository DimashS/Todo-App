package com.dimash.springboot.todoapplication.controller;

import com.dimash.springboot.todoapplication.model.TodoList;
import com.dimash.springboot.todoapplication.service.TodoListService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/todo-list")
public class AppControllerTodoList {
    private final TodoListService todoListService;

    public AppControllerTodoList(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @GetMapping("/date")
    public ResponseEntity<List<TodoList>> getList(@RequestParam(value = "person_id") Long personId,
                                                  @Valid @RequestParam(value = "creation_date", required = false)
                                                  LocalDate date) {
        if (date != null) {
            List<TodoList> todoLists = todoListService.getList(personId, date);
            return ResponseEntity.ok(todoLists);
        }
        return ResponseEntity.ok(todoListService.getList(personId));
    }

    @GetMapping("/name")
    public ResponseEntity<List<TodoList>> getList(@RequestParam(value = "person_id") Long personId,
                                                  @RequestParam(value = "name") String name) {
        List<TodoList> todoLists = todoListService.getList(personId, name);
        return ResponseEntity.ok(todoLists);
    }

    @PostMapping
    public ResponseEntity<TodoList> createList(@RequestParam(value = "person_id") Long id,
                                               @RequestBody TodoList todoList) {
        TodoList newTodoList = todoListService.createList(id, todoList);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodoList);
    }

    // send URL in id, and in body request?
    @PutMapping("/{todoListId}")
    public ResponseEntity<TodoList> updateList(@PathVariable Long todoListId,
                                               @RequestBody TodoList updatedTodoList) {
        TodoList todoList = todoListService.updateList(todoListId, updatedTodoList);
        return ResponseEntity.ok(todoList);
    }

    @DeleteMapping("/{todoListId}")
    public ResponseEntity<Void> deleteList(@PathVariable Long todoListId) {
        try {
            todoListService.deleteList(todoListId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
