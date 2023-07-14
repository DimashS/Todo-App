package com.dimash.springboot.todoapplication.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Table(name = "item")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private int completed;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;

    @DateTimeFormat(pattern = "HH.mm.ss")
    private LocalTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    private TodoList todoList;
}
