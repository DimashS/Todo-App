package com.dimash.springboot.todoapplication.Controllers;

import com.dimash.springboot.todoapplication.Model.TodoList;
import com.dimash.springboot.todoapplication.Service.ItemsService;
import com.dimash.springboot.todoapplication.Service.TodoListService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/todo-list")
public class AppControllerTodoList {
    @Autowired
    private final TodoListService todoListService;

    public AppControllerTodoList(ItemsService itemsService, TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @GetMapping
    public ResponseEntity<List<TodoList>> getAllLists(@RequestParam(value = "person_id") Long personId) {
        try {
            List<TodoList> todoLists = todoListService.getTodoList(personId);
            return ResponseEntity.ok(todoLists);
        } catch (Exception e) {
            throw new IllegalArgumentException("Incorrect id" + personId);
        }
    }

    @GetMapping
    public ResponseEntity<List<TodoList>> getAllLists(@RequestParam(value = "person_id") Long personId,
                                                      @RequestParam(value = "creation_date", required = false)
                                                      LocalDate date) {
        try {
            List<TodoList> todoLists = todoListService.getByIdAndCreationDate(personId, date);
            return ResponseEntity.ok(todoLists);
        } catch (Exception e) {
            throw new IllegalArgumentException("Something wrong");
        }
    }

    @PostMapping
    public ResponseEntity<TodoList> createTodoList(@RequestParam(value = "person_id") Long id,
                                                   @RequestBody TodoList todoList) {
        try {
            TodoList newTodoList = todoListService.createNewTodoList(id, todoList);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTodoList);
        } catch (Exception e) {
            throw new IllegalArgumentException("Something wrong");
        }
    }

    // send URL in id, and in body request?
    @PutMapping("/{todoListId}")
    public ResponseEntity<TodoList> updateTodoList(@PathVariable Long todoListId,
                                                   @RequestBody TodoList updatedTodoList) {
        try {
            TodoList todoList = todoListService.updateTodoList(todoListId, updatedTodoList);
            return ResponseEntity.ok(todoList);
        } catch (Exception e) {
            throw new IllegalArgumentException("Something wrong");
        }
    }

    @DeleteMapping("/{todoListId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long todoListId) {
        try {
            todoListService.deleteOneOfTodoList(todoListId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//
//
//
//
//
//
//
//    // @RequestBody Task task указывает, что тело HTTP-запроса должно быть преобразовано в объект
//    // Task.
//    // Spring Boot автоматически выполнит преобразование данных,
//    // полученных из тела запроса, в объект Task.
//    @PostMapping("/save")
//    public ResponseEntity<Items> createItem(@RequestBody Items items) {
//        return ResponseEntity.ok(itemsService.createNewItems(items));
//    }
//
//    // requestParam => извлечет данное поле
//    @GetMapping("/description")
//    public ResponseEntity<Items> getItemsByDescription(@RequestParam("description")
//                                                       String description) {
//        return ResponseEntity.ok(itemsService.getItemByDescription(description));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Items> getItemById(@PathVariable Long id) {
//        return ResponseEntity.ok(itemsService.getById(id));
//    }
//
//    @GetMapping("/getAll")
//    public ResponseEntity<List<Items>> getAllItems() {
//        return ResponseEntity.ok(itemsService.getAllItems());
//    }
//
//    @GetMapping("/completedTasks")
//    public ResponseEntity<List<Items>> getAllCompletedItems() {
//        return ResponseEntity.ok(itemsService.getAllCompletedItems());
//    }
//
//    @GetMapping("/incompletedTasks")
//    public ResponseEntity<List<Items>> getAllIncompleteItems() {
//        return ResponseEntity.ok(itemsService.getAllCompletedFalseItems());
//    }
//
//    @GetMapping("/createdDate")
//    public ResponseEntity<List<Items>> getByIdAndCreationDate
//            (@RequestParam("createdDate")
//             @DateTimeFormat(pattern = "dd.MM.yyyy") Instant date) {
//        LocalDate localDate = date.atZone(ZoneId.systemDefault()).toLocalDate();
//        List<Items> itemsList = itemsService.getByIdAndCreationDate(Instant.from(localDate));
//        return ResponseEntity.ok(itemsList);
//    }
//
//    @GetMapping("/modifiedDate")
//    public ResponseEntity<List<Items>> getByModifiedDate
//            (@RequestParam("modifiedDate")
//             @DateTimeFormat(pattern = "dd.MM.yyyy") Instant date) {
//        LocalDate localDate = date.atZone(ZoneId.systemDefault()).toLocalDate();
//        List<Items> itemsList = itemsService.getByModifiedDate(Instant.from(localDate));
//        return ResponseEntity.ok(itemsList);
//    }
//
//    @GetMapping("/personList/{id}")
//    public ResponseEntity<List<Items>> getByPersonId(Long id) {
//        return ResponseEntity.ok(itemsService.getByPersonId(id));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Items> updateItems (@PathVariable Long id, @RequestBody Items items) {
//        items.setId(id);
//        return ResponseEntity.ok(itemsService.updateItems(items));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Boolean> getAllTasks(@PathVariable Long id) {
//        itemsService.deleteItems(id);
//        return ResponseEntity.ok(true);
//    }
}
