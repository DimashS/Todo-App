package com.dimash.springboot.todoapplication.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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
    private Long id;

    private String name;

    @DateTimeFormat(pattern = "dd:mm:yyyy")
    private LocalDate creationDate;
    // here we have relation ManyToOne

    @ManyToOne(fetch = FetchType.EAGER)
    private Person person;
    // here we have relation OneToMany => One List has a lot of items

    @OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL)
    private List<Item> itemList = new ArrayList<>();
}
