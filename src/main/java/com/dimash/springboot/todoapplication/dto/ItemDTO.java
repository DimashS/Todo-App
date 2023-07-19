package com.dimash.springboot.todoapplication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private String description;
    private boolean completed;
    private LocalDate date;
    private LocalTime time;
}
