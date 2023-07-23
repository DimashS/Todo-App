package com.dimash.springboot.todoapplication.controller;

import com.dimash.springboot.todoapplication.dto.TodoListDTO;
import com.dimash.springboot.todoapplication.model.Person;
import com.dimash.springboot.todoapplication.model.TodoList;
import com.dimash.springboot.todoapplication.service.serviceImpl.TodoListServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/todo-list")
public class AppControllerTodoList {
    private final TodoListServiceImpl todoListService;

    private final ModelMapper modelMapper;

    public AppControllerTodoList(TodoListServiceImpl todoListService, ModelMapper modelMapper) {
        this.todoListService = todoListService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/date")
    public ResponseEntity<List<TodoListDTO>> getList(@Valid @RequestParam(value = "creation_date", required = false)
                                                     LocalDateTime date) {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (date != null) {
            return ResponseEntity.ok(todoListService.get(person.getId(), date).stream()
                    .map(this::convertToDTO).toList());

        }
        return ResponseEntity.ok(todoListService.get(person.getId()).stream().map(this::convertToDTO).toList());
    }

    @GetMapping("/name")
    public ResponseEntity<List<TodoListDTO>> getList(@RequestParam(value = "name") String name) {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(todoListService.get(person.getId(), name).stream().map(this::convertToDTO).toList());
    }

    @PostMapping
    public ResponseEntity<TodoListDTO> createList(@RequestBody TodoListDTO todoListDTO) {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TodoListDTO newTodoListDTO = convertToDTO(todoListService.create(person.getId(), convertToEntity(todoListDTO)));
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodoListDTO);
    }

    // send URL in id, and in body request?
    @PreAuthorize("@todoListAccessServiceImpl.canUpdate(#todoListId)")
    @PutMapping("/{todoListId}")
    public ResponseEntity<TodoListDTO> updateList(@PathVariable Long todoListId,
                                                  @RequestBody TodoList updatedTodoList) {
        TodoList todoList = todoListService.update(todoListId, updatedTodoList);
        return ResponseEntity.ok(convertToDTO(todoList));
    }

    @PreAuthorize("@todoListAccessServiceImpl.canDelete(#todoListId)")
    @DeleteMapping("/{todoListId}")
    public ResponseEntity<Void> deleteList(@PathVariable Long todoListId) {
        try {
            todoListService.delete(todoListId);
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
