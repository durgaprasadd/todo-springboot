package com.example.todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ToDoServiceTest {
    @Mock
    private ToDoRepository toDoRepository;

    private ToDoService toDoService;

    @BeforeEach
    void setUp() {
        toDoService = new ToDoService(toDoRepository);
    }

    @Test
    void getAllToDos() {
        ToDo todoSample = new ToDo("Todo Sample 1", true);

        List<ToDo> allTodos = asList(todoSample);
        when(toDoRepository.findAll()).thenReturn(allTodos);

        List<ToDo> toDoList = toDoService.findAll();

        assertThat(toDoList, is(allTodos));
    }
}
