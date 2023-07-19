package com.dimash.springboot.todoapplication.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    @NotEmpty(message = "Name can't be empty")
    @NotBlank(message = "Name can't be empty")
    @Size(min = 2,max = 30, message = "Your name should be between 2 and 30 characters")
    private String name;
    @Min(value = 8, message = "Excuse me, age should be greater than 8")
    private int yearOfBirth;
    private String role;

}
