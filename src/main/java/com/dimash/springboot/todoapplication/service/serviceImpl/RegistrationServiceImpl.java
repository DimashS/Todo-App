package com.dimash.springboot.todoapplication.service.serviceImpl;

import com.dimash.springboot.todoapplication.dto.PersonDTO;
import com.dimash.springboot.todoapplication.model.Person;
import com.dimash.springboot.todoapplication.repository.PersonRepository;
import com.dimash.springboot.todoapplication.service.RegistrationService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public Person registerUser(PersonDTO personDTO) {
        if (personDTO.getName() == null || personDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Username can't be empty");
        }
        if (personRepository.findByUsername(personDTO.getName()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        Person person = new Person();
        person.setUsername(personDTO.getName());
        person.setPassword(passwordEncoder.encode(personDTO.getPassword()));
        person.setRole("ROLE_USER");
        person.setYearOfBirth(personDTO.getYearOfBirth());
        person.setCreatedDate(LocalDateTime.now());
        person.setLastModifiedDate(LocalDateTime.now());

        personRepository.save(person);
        return person;
    }
}
