package com.dimash.springboot.todoapplication.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "todo-list-items")
@AllArgsConstructor
@NoArgsConstructor
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
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDateTime modifiedDate;

    @Column(name = "role")
    private String role;

    @Column(name = "person_id")
    private int personId;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
