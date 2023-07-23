package com.dimash.springboot.todoapplication.service.serviceImpl;

import com.dimash.springboot.todoapplication.model.Person;
import com.dimash.springboot.todoapplication.repository.PersonRepository;
import com.dimash.springboot.todoapplication.service.RegistrationService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationServiceImpl(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void registerUser(Person person) {
        if (personRepository.findByUsername(person.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        person.setYearOfBirth(person.getYearOfBirth());
        person.setUsername(person.getUsername());
        person.setCreatedDate(LocalDateTime.now());
        person.setLastModifiedDate(LocalDateTime.now());
        personRepository.save(person);
    }
}
