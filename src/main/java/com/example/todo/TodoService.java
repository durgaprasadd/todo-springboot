package com.example.todo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {

    public List<ToDo> findAll() {
        return new ArrayList<>();
    }
}
