package com.dimash.springboot.todoapplication.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "todo_list")
@Entity
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "createdDate")
    private LocalDate creationDate;
    // here we have relation ManyToOne
    @ManyToOne(fetch = FetchType.EAGER)
    private Person person;
    // here we have relation OneToMany => One List has a lot of items
    @OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Items> itemList = new ArrayList<>();
}
