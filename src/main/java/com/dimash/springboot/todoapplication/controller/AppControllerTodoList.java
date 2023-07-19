package com.dimash.springboot.todoapplication.controller;

import com.dimash.springboot.todoapplication.dto.TodoListDTO;
import com.dimash.springboot.todoapplication.model.TodoList;
import com.dimash.springboot.todoapplication.service.TodoListService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/todo-list")
public class AppControllerTodoList {
    private final TodoListService todoListService;

    private final ModelMapper modelMapper;

    public AppControllerTodoList(TodoListService todoListService, ModelMapper modelMapper) {
        this.todoListService = todoListService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/date")
    public ResponseEntity<List<TodoListDTO>> getList(@RequestParam(value = "person_id") Long personId,
                                                     @Valid @RequestParam(value = "creation_date", required = false)
                                                     LocalDateTime date) {
        if (date != null) {
            return ResponseEntity.ok(todoListService.getList(personId,date).stream()
                    .map(this::convertToDTO).toList());

        }
        return ResponseEntity.ok(todoListService.getList(personId).stream().map(this::convertToDTO).toList());
    }

    @GetMapping("/name")
    public ResponseEntity<List<TodoListDTO>> getList(@RequestParam(value = "person_id") Long personId,
                                                     @RequestParam(value = "name") String name) {
        return ResponseEntity.ok(todoListService.getList(personId, name).stream().map(this::convertToDTO).toList());
    }

    @PostMapping
    public ResponseEntity<TodoListDTO> createList(@RequestParam(value = "person_id") Long id,
                                                  @RequestBody TodoListDTO todoListDTO) {
        TodoListDTO newTodoListDTO = convertToDTO(todoListService.createList(id, convertToEntity(todoListDTO)));
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodoListDTO);
    }

    // send URL in id, and in body request?
    @PutMapping("/{todoListId}")
    public ResponseEntity<TodoListDTO> updateList(@PathVariable Long todoListId,
                                                  @RequestBody TodoList updatedTodoList) {
        TodoList todoList = todoListService.updateList(todoListId, updatedTodoList);
        return ResponseEntity.ok(convertToDTO(todoList));
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


    private TodoListDTO convertToDTO(TodoList todoList) {
        return modelMapper.map(todoList, TodoListDTO.class);
    }

    private TodoList convertToEntity(TodoListDTO todoListDTO) {
        return modelMapper.map(todoListDTO, TodoList.class);
    }
}
