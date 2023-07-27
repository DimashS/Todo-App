package com.dimash.springboot.todoapplication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person extends BaseEntity {

    @NotEmpty
    @NotBlank
    @Column(name = "name", unique = true)
    private String username;

    private int yearOfBirth;

    @NotEmpty
    @NotBlank
    private String password;

    private String role;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<TodoList> todoLists = new ArrayList<>();


}
