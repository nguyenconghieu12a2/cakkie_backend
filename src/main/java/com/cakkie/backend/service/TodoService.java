package com.cakkie.backend.service;

import com.cakkie.backend.model.Todo;

import java.util.List;

//cai nay cua controller
public interface TodoService {
//    provide all method we need for this model
    List<Todo> getAllTodo();
    Todo createTodo(Todo todo);
    void deleteTodo(Long id) throws Exception;
    Todo updateTodo (Todo todo);
    Todo getTodoByTitle(String value);
}
