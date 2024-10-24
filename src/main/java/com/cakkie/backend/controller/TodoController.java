package com.cakkie.backend.controller;

import com.cakkie.backend.api.TodoAPI;
import com.cakkie.backend.model.Todo;
import com.cakkie.backend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping("/api")
    public TodoAPI homeController(){
        TodoAPI todoApi = new TodoAPI();
        todoApi.setMessage("welcome to TodoAPI");
        todoApi.setStatus(true);
        return todoApi;
    }

    @GetMapping("/api/todo")
    public List<Todo> getAllTodo(){
        return todoService.getAllTodo();
    }
    @PostMapping("api/todo")
    public Todo createTodo(@RequestBody Todo todo){
        return todoService.createTodo(todo);
    }
    @DeleteMapping("/api/todo/{id}")
    public TodoAPI deleteTodo(@PathVariable Long id) throws Exception {
        todoService.deleteTodo(id);
        TodoAPI todoApi = new TodoAPI();
        todoApi.setMessage("todo deleted successfully");
        todoApi.setStatus(true);
        return todoApi;
    }

    @PostMapping("/api/todo/{value}")
    public Todo getTodoTitle(@PathVariable String value){
        return todoService.getTodoByTitle(value);
    }
}
