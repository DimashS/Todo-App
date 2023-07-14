package com.dimash.springboot.todoapplication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
