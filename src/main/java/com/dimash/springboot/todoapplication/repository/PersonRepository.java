package com.dimash.springboot.todoapplication.repository;

import com.dimash.springboot.todoapplication.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(String name);
}
