package com.dimash.springboot.todoapplication.response;

import com.dimash.springboot.todoapplication.model.Person;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginResponse {
    private final Person person;
}
