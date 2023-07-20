package com.dimash.springboot.todoapplication.service.serviceImpl;

import com.dimash.springboot.todoapplication.model.Person;
import com.dimash.springboot.todoapplication.model.TodoList;
import com.dimash.springboot.todoapplication.repository.PersonRepository;
import com.dimash.springboot.todoapplication.repository.TodoListRepository;
import com.dimash.springboot.todoapplication.service.TodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// get,create,delete,update


@Service
@RequiredArgsConstructor
public class TodoListServiceImpl implements TodoListService {
    private final TodoListRepository todoListRepository;
    private final PersonRepository personRepository;

    @Override
    public TodoList create(Long personId, TodoList todoList) {
        Person person1 = personRepository.findById(personId).orElseThrow(()
                -> new RuntimeException("Person not found"));
        todoList.setPerson(person1);
        todoList.setCreatedDate(LocalDateTime.now());
        todoList.setLastModifiedDate(LocalDateTime.now());
        return todoListRepository.save(todoList);
    }

    @Override
    public List<TodoList> get(Long personId, String name) {
        Person person1 = personRepository.findById(personId).orElseThrow(()
                -> new RuntimeException("Person not found"));
        return todoListRepository.findTodoListByIdAndNameLike(person1.getId(), name);
    }

    @Override
    public List<TodoList> get(Long personId) {
        return todoListRepository.findByPersonId(personId);
    }

    @Override
    public List<TodoList> get(Long todoListId, LocalDateTime localDate) {
        return todoListRepository.findByIdAndCreatedDate(todoListId, localDate);
    }

    @Override
    public TodoList update(Long todoListId, TodoList updatedTodoList) {
        TodoList todoList1 = todoListRepository.findById(todoListId).orElseThrow(()
                -> new RuntimeException("There is wrong id"));
        todoList1.setName(updatedTodoList.getName());
        todoList1.setLastModifiedDate(LocalDateTime.now());
        return todoListRepository.save(todoList1);
    }

    @Override
    public void delete(Long id) {
        TodoList todoList = todoListRepository.findById(id).orElseThrow(() -> new RuntimeException("There is wrong id"));
        todoListRepository.deleteById(todoList.getId());
    }


}
