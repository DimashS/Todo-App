package com.dimash.springboot.todoapplication.service;

import com.dimash.springboot.todoapplication.dto.PersonDTO;
import com.dimash.springboot.todoapplication.model.Person;

public interface RegistrationService {
    public Person registerUser(PersonDTO personDTO);
}
