package com.dimash.springboot.todoapplication.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationDTO {
    @NotEmpty(message = "shouldn't be empty")
    @Size(min = 1, max = 30, message = "name could be between 1 and 30 characters")
    private String name;
    private String password;
}
