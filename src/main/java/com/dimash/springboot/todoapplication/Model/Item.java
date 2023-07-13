package com.dimash.springboot.todoapplication.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Table(name = "items")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "completed")
    private int completed;

    @Column(name = "created_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate createdDate;

    @Column(name = "modified_date")
    @DateTimeFormat(pattern = "HH.mm.ss")
    private LocalTime modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private TodoList todoList;
}
