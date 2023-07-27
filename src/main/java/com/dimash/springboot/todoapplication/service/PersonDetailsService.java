package com.dimash.springboot.todoapplication.service;

import com.dimash.springboot.todoapplication.model.Person;
import com.dimash.springboot.todoapplication.repository.PersonRepository;
import com.dimash.springboot.todoapplication.security.PersonDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;

    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public PersonDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByUsername(username);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException("Person not found");
        }
        return new PersonDetails(person.get());
    }

}
