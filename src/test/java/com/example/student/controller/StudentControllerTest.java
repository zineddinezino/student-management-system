package com.example.student.controller;

import com.example.student.dto.StudentDto;
import com.example.student.service.StudentService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

class StudentControllerTest {
    @InjectMocks
    private StudentController studentController;
    @Mock
    private StudentService studentService;
    private MockMvc mockMvc;
    @BeforeEach
    void setup() throws Exception{
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }
    @Test
    void should_return_student_when_get_student_by_id() throws Exception{
        var studentDto = buildStudentDto();
        Mockito.when(studentService.getStudentById(1L)).thenReturn(studentDto);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/students/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Zineddine")));
    }

    private StudentDto buildStudentDto() {
        return StudentDto.builder()
                .studentId(1L)
                .firstName("Zineddine")
                .build();
    }
}