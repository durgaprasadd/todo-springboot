package com.example.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("toDoRepository")
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    @Query(value = "SELECT * from to_do where title=(:title)", nativeQuery = true)
    List<ToDo> findByName(@Param(value = "title") String title);
}
