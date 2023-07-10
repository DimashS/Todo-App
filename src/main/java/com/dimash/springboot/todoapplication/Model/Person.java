package com.dimash.springboot.todoapplication.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    @NotEmpty
    @NotBlank
    private String username;
    @Column(name = "year_of_birth")
    private int yearOfBirth;
    @NotEmpty
    @NotBlank
    @Column(name = "password")
    private String password;
    @Column(name = "roles")
    private String roles;
    @OneToMany(mappedBy = "person",cascade = CascadeType.ALL)
    private List<Items> itemsList = new ArrayList<>();


}
