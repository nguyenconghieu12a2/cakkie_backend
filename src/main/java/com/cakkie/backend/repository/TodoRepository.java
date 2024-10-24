package com.cakkie.backend.repository;

import com.cakkie.backend.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Query(value = "SELECT t FROM Todo t")
    public List<Todo> getAllTodo();

    @Query(value = "SELECT t FROM Todo t where t.title =:val")
    public Todo getTodoByTitle(@Param("val") String title);
}
