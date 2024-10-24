package com.cakkie.backend.implement;

import com.cakkie.backend.model.Todo;
import com.cakkie.backend.repository.TodoRepository;
import com.cakkie.backend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoImplement implements TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Override
    public List<Todo> getAllTodo() {
        return todoRepository.getAllTodo();
    }
    @Override
    public Todo getTodoByTitle(String value){
        return todoRepository.getTodoByTitle(value);
    }

    @Override
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(Long id) throws Exception {
        Todo todo = todoRepository.findById(id).orElseThrow(()->new Exception("todo not exist"));

        todoRepository.delete(todo);
    }

    @Override
    public Todo updateTodo(Todo todo) {
        return null;
    }


}
