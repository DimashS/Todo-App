package com.dimash.springboot.todoapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ItemDTO {
    private String description;
    private boolean completed;
    private LocalDate date;
    private LocalTime time;
}
