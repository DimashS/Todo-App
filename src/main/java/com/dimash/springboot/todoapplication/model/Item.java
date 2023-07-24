package com.dimash.springboot.todoapplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Table(name = "item")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Item extends BaseEntity {

    private String description;

    private int completed;

    private LocalDate date;

    private LocalTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    private TodoList todoList;
}
