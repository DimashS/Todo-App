package com.dimash.springboot.todoapplication.Service;

import com.dimash.springboot.todoapplication.Model.Items;
import com.dimash.springboot.todoapplication.Repository.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ItemsService {
    @Autowired
    private ItemsRepository itemsRepository;

    public ItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    public Items createNewItems(Items items) {
        return itemsRepository.save(items);
    }

    public Items getItemByDescription(String description) {
        return itemsRepository.findByDescription(description);
    }

    public Items getById(Long id) {
        return itemsRepository.getById(id);
    }

    public List<Items> getAllItems() {
        return itemsRepository.findAll();
    }

    public List<Items> getAllCompletedItems() {
        return itemsRepository.findByCompletedTrue();
    }

    public List<Items> getAllNonCompletedItems() {
        return itemsRepository.findByCompletedFalse();
    }

    public List<Items> getByCreatedDate(Instant instant) {
        return itemsRepository.getByCreatedDate(instant);
    }

    public List<Items> getByModifiedDate(Instant instant) {
        return itemsRepository.getByModifiedDate(instant);
    }

    public List<Items> getByPersonId(Long id) {
        return itemsRepository.getByPersonId(id);
    }

    public Items updateItems(Items items) {
        return itemsRepository.save(items);
    }

    public void deleteItems(Long items) {
        itemsRepository.deleteItems(items);
    }
}
