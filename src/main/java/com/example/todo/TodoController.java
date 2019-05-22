package com.example.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {
    @Autowired
    private ToDoService todoService;

    private ResponseEntity<List<ToDo>> getAllToDos() {
        return new ResponseEntity<>(todoService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/todos")
    ResponseEntity<ToDo> create(@RequestBody ToDo toDo) {
        return new ResponseEntity<>(todoService.save(toDo), HttpStatus.CREATED);
    }

    @GetMapping("/todos")
    ResponseEntity<List<ToDo>> getTodo(@RequestParam(value = "title", defaultValue = "Null") String title) {
        if (title.equals("Null")) return getAllToDos();
        return new ResponseEntity<>(todoService.findByName(title), HttpStatus.OK);
    }
}
