package com.dimash.springboot.todoapplication.service;

import com.dimash.springboot.todoapplication.model.Person;
import com.dimash.springboot.todoapplication.model.TodoList;
import com.dimash.springboot.todoapplication.repository.PersonRepository;
import com.dimash.springboot.todoapplication.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

// get,createList,delete,update


@Service
public class TodoListService {
    @Autowired
    private final TodoListRepository todoListRepository;
    @Autowired
    private final PersonRepository personRepository;

    public TodoListService(TodoListRepository todoListRepository, PersonRepository personRepository) {
        this.todoListRepository = todoListRepository;
        this.personRepository = personRepository;
    }

    public TodoList createList(Long personId, TodoList todoList) {
        Person person1 = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        todoList.setPerson(person1);
        return todoListRepository.save(todoList);
    }

    public List<TodoList> getList(Long personId, String name) {
        Person person1 = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        return todoListRepository.findTodoListByIdAndNameLike(person1.getId(), name);
    }

    public List<TodoList> getList(Long personId) {
        return todoListRepository.findByPersonId(personId);
    }

    public List<TodoList> getList(Long todoListId, LocalDate localDate) {
        return todoListRepository.findByIdAndCreationDate(todoListId, localDate);
    }

    public TodoList updateList(Long todoListId, TodoList updatedTodoList) {
        TodoList todoList1 = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new RuntimeException("There is wrong id"));
        todoList1.setName(updatedTodoList.getName());
        todoList1.setCreationDate(updatedTodoList.getCreationDate());
        return todoListRepository.save(todoList1);
    }


    public void deleteList(Long id) {
        TodoList todoList = todoListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is wrong id"));
        todoListRepository.deleteById(todoList.getId());
    }


}
