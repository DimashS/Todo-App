package com.dimash.springboot.todoapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class TodoList extends BaseEntity {

    private String name;
    // here we have relation ManyToOne

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Person person;
    // here we have relation OneToMany => One List has a lot of items


    @OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Item> itemList;
}
