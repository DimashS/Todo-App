package com.dimash.springboot.todoapplication.Controllers;

import com.dimash.springboot.todoapplication.Model.Items;
import com.dimash.springboot.todoapplication.Service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/Items")
public class AppController {
    @Autowired
    private final ItemsService itemsService;

    public AppController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    // @RequestBody Task task указывает, что тело HTTP-запроса должно быть преобразовано в объект
    // Task.
    // Spring Boot автоматически выполнит преобразование данных,
    // полученных из тела запроса, в объект Task.
    @PostMapping("/save")
    public ResponseEntity<Items> createItem(@RequestBody Items items) {
        return ResponseEntity.ok(itemsService.createNewItems(items));
    }

    // requestParam => извлечет данное поле
    @GetMapping("/description")
    public ResponseEntity<Items> getItemsByDescription(@RequestParam("description")
                                                       String description) {
        return ResponseEntity.ok(itemsService.getItemByDescription(description));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Items> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemsService.getById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Items>> getAllItems() {
        return ResponseEntity.ok(itemsService.getAllItems());
    }

    @GetMapping("/completedTasks")
    public ResponseEntity<List<Items>> getAllCompletedItems() {
        return ResponseEntity.ok(itemsService.getAllCompletedItems());
    }

    @GetMapping("/incompletedTasks")
    public ResponseEntity<List<Items>> getAllIncompletedItems() {
        return ResponseEntity.ok(itemsService.getAllCompletedFalseItems());
    }

    @GetMapping("/createdDate")
    public ResponseEntity<List<Items>> getByCreatedDate
            (@RequestParam("createdDate")
             @DateTimeFormat(pattern = "dd.MM.yyyy") Instant date) {
        LocalDate localDate = date.atZone(ZoneId.systemDefault()).toLocalDate();
        List<Items> itemsList = itemsService.getByCreatedDate(Instant.from(localDate));
        return ResponseEntity.ok(itemsList);
    }

    @GetMapping("/modifiedDate")
    public ResponseEntity<List<Items>> getByModifiedDate
            (@RequestParam("modifiedDate")
             @DateTimeFormat(pattern = "dd.MM.yyyy") Instant date) {
        LocalDate localDate = date.atZone(ZoneId.systemDefault()).toLocalDate();
        List<Items> itemsList = itemsService.getByModifiedDate(Instant.from(localDate));
        return ResponseEntity.ok(itemsList);
    }

    @GetMapping("/personList/{id}")
    public ResponseEntity<List<Items>> getByPersonId(Long id) {
        return ResponseEntity.ok(itemsService.getByPersonId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Items> updateItems (@PathVariable Long id, @RequestBody Items items) {
        items.setId(id);
        return ResponseEntity.ok(itemsService.updateItems(items));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> getAllTasks(@PathVariable Long id) {
        itemsService.deleteItems(id);
        return ResponseEntity.ok(true);
    }
}
