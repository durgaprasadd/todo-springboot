package com.example.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToDoService todoService;

    @Test
    public void shouldGiveAllTodos() throws Exception {
        List<ToDo> todoList = new ArrayList<>();
        todoList.add(new ToDo(1L, "Eat thrice", true));
        todoList.add(new ToDo(2L, "Sleep twice", true));
        when(todoService.findAll()).thenReturn(todoList);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());
    }

    @Test
    void shouldGiveSpecificTodo() throws Exception {
        List<ToDo> expectedList = new ArrayList<>();
        expectedList.add(new ToDo(1L, "Eat thrice", true));
        when(todoService.findByName("Eat thrice")).thenReturn(expectedList);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/todos?title=Eat thrice")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(print());
    }


    @Test
    void shouldSaveTodo() throws Exception {
        ToDo toDo = new ToDo("Sleep twice", false);
        when(todoService.save(any(ToDo.class))).thenReturn(toDo);
        ObjectMapper objectMapper = new ObjectMapper();
        String stringifiedTodo = objectMapper.writeValueAsString(toDo);
        ResultActions result = mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringifiedTodo)
        );
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(toDo.getTitle()))
                .andExpect(jsonPath("$.completed").value(toDo.isCompleted()))
                .andExpect(jsonPath("$.id").value(toDo.getId()));
    }
}
