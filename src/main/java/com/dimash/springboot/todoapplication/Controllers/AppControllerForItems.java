package com.dimash.springboot.todoapplication.Controllers;

import com.dimash.springboot.todoapplication.Model.Items;
import com.dimash.springboot.todoapplication.Model.TodoList;
import com.dimash.springboot.todoapplication.Service.ItemsService;
import com.dimash.springboot.todoapplication.Service.TodoListService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/item")
public class AppControllerForItems {

    @Autowired
    private ItemsService itemsService;

    @GetMapping
    public ResponseEntity<List<Items>> getAllItems(@RequestParam(value = "todoListId") Long todoListId,
                                                   @Valid @RequestParam(value = "date", required = false) LocalDate localDate) {
        // if we have required = false, would have we get troubles with services methods
        List<Items> itemsList = itemsService.getItemsFromTodoList(todoListId, localDate);
        return ResponseEntity.ok(itemsList);
    }

    @PostMapping
    public ResponseEntity<Items> createItem(@RequestParam(value = "todoListId") Long todoListId, @RequestBody Items items) {
        Items createdItem = itemsService.createItemForTodo(todoListId, items);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);

    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Items> updateItem(@PathVariable Long itemId, @RequestBody Items updatedItem) {
        Items updatedItems = itemsService.updateItemForTodoList(itemId, updatedItem);
        return ResponseEntity.ok(updatedItems);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        boolean deleted = itemsService.deleteItems(itemId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
