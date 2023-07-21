package com.dimash.springboot.todoapplication.service;

import com.dimash.springboot.todoapplication.model.Person;
import com.dimash.springboot.todoapplication.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerUser(Person person) {
        if (personRepository.findByUsername(person.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole(person.getRole());
        person.setYearOfBirth(person.getYearOfBirth());
        person.setUsername(person.getUsername());
        personRepository.save(person);
    }
}