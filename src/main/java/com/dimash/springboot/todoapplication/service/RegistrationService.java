package com.dimash.springboot.todoapplication.service;

import com.dimash.springboot.todoapplication.dto.PersonDTO;

public interface RegistrationService {
    void registerUser(PersonDTO personDTO);
}
