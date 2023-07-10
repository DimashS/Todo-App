package com.dimash.springboot.todoapplication.Repository;

import com.dimash.springboot.todoapplication.Model.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Long> {
    public Items findByDescription(String description);

    public List<Items> findByCompletedTrue();

    public List<Items> findByCompletedFalse();

    public List<Items> findAll();

    public Items getById(Long id);


    public List<Items> getByCreatedDate(Instant instant);


    public List<Items> getByModifiedDate(Instant instant);


    public List<Items> getByPersonId(Long id);

    public Items deleteItems(Long id);
}